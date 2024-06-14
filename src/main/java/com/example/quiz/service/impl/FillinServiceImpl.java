package com.example.quiz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.OptionType;
import com.example.quiz.constants.ResMessage;
import com.example.quiz.entity.Quiz;
import com.example.quiz.entity.Response;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.repository.ResponseDao;
import com.example.quiz.service.ifs.FillinService;
import com.example.quiz.valueobject.BasicResponse;
import com.example.quiz.valueobject.Feedback;
import com.example.quiz.valueobject.FeedbackDetail;
import com.example.quiz.valueobject.FeedbackRequest;
import com.example.quiz.valueobject.FeedbackResponse;
import com.example.quiz.valueobject.Fillin;
import com.example.quiz.valueobject.FillinRequest;
import com.example.quiz.valueobject.Question;
import com.example.quiz.valueobject.StatisticsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FillinServiceImpl implements FillinService {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private ResponseDao responseDao;

	@Override
	public BasicResponse fillin(FillinRequest request) {
		// �Ѽ��ˬd
		BasicResponse checkResult = checkParams(request);
		if (checkResult != null) {
			return checkResult;
		}
		// �ˬd�P�@�ӹq�ܸ��X�O�_�����ƶ�g�P�@�i�ݨ�
		if (responseDao.existsByQuizIDAndPhone(request.getQuizId(), request.getPhone())) {
			return new BasicResponse(ResMessage.DUPLICATED_FILLIN.getCode(), //
					ResMessage.DUPLICATED_FILLIN.getMessage());
		}
		// �ˬd quiz_id �O�_�s�b��DB��
		// �]������|��� req �������׻P�D�ت��ﶵ�O�_�ŦX�A�ҥH�n�� findById
		Optional<Quiz> op = quizDao.findById(request.getQuizId());
		if (op.isEmpty()) {
			return new BasicResponse(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		Quiz quiz = op.get();
		// �q quiz �����X questions �r��
		String questionsStr = quiz.getQuestions();
		// �ϥ� ObjectMapper �N questionsStr �ন List<Question>
		// fillinStr �n���Ŧr��A���M�w�]�|�O null
		// �Y fillinStr = null�A������� fillinStr =
		// mapper.writeValueAsString(req.getqIdAnswerMap());
		// �����o�쪺���G��^�� fillinStr �ɡA�|����
		String fillinStr = "";
		try {
			// �إߥ��T�� List<Fillin>
			List<Fillin> newFillinList = new ArrayList<>();
			// �إߤw�s�W�� question_id List
			List<Integer> qIds = new ArrayList<>();
			List<Question> quList = mapper.readValue(questionsStr, new TypeReference<>() {
			});
			// ���C�@�� Question �P fillin ��������
			for (Question item : quList) { // 1, 2, 3
				List<Fillin> fillinList = request.getFillinList();
				for (Fillin fillin : fillinList) { // 1, 3
					// id ���@�P�A���L
					if (item.getId() != fillin.getQuestionId()) {
						continue;
					}
					// �p�G qIds �w�g���]�t���D�s���A��ܤw�ˬd�L���D��
					// ���q�Ψӱư� request �������ƪ����D�s��
					if (qIds.contains(fillin.getQuestionId())) {
						continue;
					}
					// �N�w�s�W���D���D���[�J
					qIds.add(fillin.getQuestionId()); // 1, 3
					// �s�W�ۦP�D���� fillin
					// �������� fillin �[�� list ����]�O�G
					// �W�����{���X���F�� question_id �M answer ���ˬd�A�ҥH��l���ݩʤ��e�i��O���X�k��
					// �����ϥ� Question item ���ȬO�]���o�ǭȳ��O�q DB �ӡA���w���ˬd�L
					newFillinList.add(new Fillin(item.getId(), item.getTitle(), //
							item.getOptions(), fillin.getAnswer(), item.getType(), //
							item.isNecessary()));
					// �ˬd�ﶵ�P����
					checkResult = checkOptionAnswer(item, fillin);
					if (checkResult != null) {
						return checkResult;
					}
				}
				// ���`�����p�O: ���D�O����A�M��S���^���A�C�]���@�D�AqIds �N�|�]�t�ӥ��񪺰��D�� id
				// �]���Y���D�O����A�� qId �S�S���]�t���D�� id�A�N��ܨS���^�����D
				if (item.isNecessary() && !qIds.contains(item.getId())) {
					return new BasicResponse(ResMessage.ANSWER_IS_REQUIRED.getCode(),
							ResMessage.ANSWER_IS_REQUIRED.getMessage());
				}
			}
			fillinStr = mapper.writeValueAsString(newFillinList);
		} catch (JsonProcessingException e) {
			return new BasicResponse(ResMessage.JSON_PROCESSING_EXCEPTION_ERROR.getCode(),
					ResMessage.JSON_PROCESSING_EXCEPTION_ERROR.getMessage());
		}
		responseDao.save(new Response(request.getQuizId(), request.getName(), request.getPhone(), request.getEmail(), //
				request.getAge(), fillinStr));
		return new BasicResponse(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	private BasicResponse checkParams(FillinRequest request) {
		if (request.getQuizId() <= 0) {
			return new BasicResponse(ResMessage.PARAM_QUIZ_ID_ERROR.getCode(), //
					ResMessage.PARAM_QUIZ_ID_ERROR.getMessage());
		}
		if (!StringUtils.hasText(request.getName())) {
			return new BasicResponse(ResMessage.PARAM_NAME_IS_REQUIRED.getCode(), //
					ResMessage.PARAM_NAME_IS_REQUIRED.getMessage());
		}
		if (!StringUtils.hasText(request.getPhone())) {
			return new BasicResponse(ResMessage.PARAM_PHONE_IS_REQUIRED.getCode(), //
					ResMessage.PARAM_PHONE_IS_REQUIRED.getMessage());
		}
		if (!StringUtils.hasText(request.getEmail())) {
			return new BasicResponse(ResMessage.PARAM_EMAIL_IS_REQUIRED.getCode(), //
					ResMessage.PARAM_EMAIL_IS_REQUIRED.getMessage());
		}
		if (request.getAge() < 12 || request.getAge() > 99) {
			return new BasicResponse(ResMessage.PARAM_AGE_NOT_QUALIFIED.getCode(), //
					ResMessage.PARAM_AGE_NOT_QUALIFIED.getMessage());
		}
		return null;
	}

	private BasicResponse checkOptionAnswer(Question item, Fillin fillin) {
		// 1. �ˬd����]�n������
		// fillin �������רS���ȡA��^���~
		if (item.isNecessary() && !StringUtils.hasText(fillin.getAnswer())) {
			return new BasicResponse(ResMessage.ANSWER_IS_REQUIRED.getCode(),
					ResMessage.ANSWER_IS_REQUIRED.getMessage());
		}
		// 2. �ư��D���O��� �� answerArray ������ > 1
		String answerStr = fillin.getAnswer();
		// �� answerStr(����) ���Φ��}�C
		String[] answerArray = answerStr.split(";");
		if (item.getType().equalsIgnoreCase(OptionType.SINGLE_CHOICE.getType()) //
				&& answerArray.length > 1) {
			return new BasicResponse(ResMessage.ANSWER_OPTION_TYPE_IS_NOT_MATCH.getCode(),
					ResMessage.ANSWER_OPTION_TYPE_IS_NOT_MATCH.getMessage());
		}
		// 3. �ư�²���D�Foption type �O text(�]�����U�ӭn�ˬd�ﶵ�P���׬O�_�@�P)
		if (item.getType().equalsIgnoreCase(OptionType.TEXT.getType())) {
			return null;
		}
		// �� options ���� Array
		String[] optionArray = item.getOptions().split(";");
		// �� optionArray �ন List�A�]���n�ϥ� List ���� contains ��k
		List<String> optionList = List.of(optionArray);
		// 4. �ˬd���׸�ﶵ�@�P
		for (String str : answerArray) {
			// ���] item.getOptions() ���ȬO: "AB;BC;C;D"
			// �ন List �᪺ optionList = ["AB", "BC", "C", "D"]
			// ���] answerArray = [AB, B]
			// for �j�餤�N�O�� AB �M B ���O�_�Q�]�t�b optionList ��
			// List �� contains ��k�O��������A�ҥH�d�Ҥ��AAB�O���]�t�AB�O�S��
			// �ư��H�U:
			// 1. ���� && ���׿ﶵ���@�P
			if (item.isNecessary() && !optionList.contains(str)) {
				return new BasicResponse(ResMessage.ANSWER_OPTIONS_IS_NOT_MATCH.getCode(),
						ResMessage.ANSWER_OPTIONS_IS_NOT_MATCH.getMessage());
			}
			// 2. �D���� && ������ && ���׿ﶵ���@�P
			if (!item.isNecessary() && StringUtils.hasText(str) && !optionList.contains(str)) {
				return new BasicResponse(ResMessage.ANSWER_OPTIONS_IS_NOT_MATCH.getCode(),
						ResMessage.ANSWER_OPTIONS_IS_NOT_MATCH.getMessage());
			}
		}
		return null;
	}

	@Override
	public FeedbackResponse feedback(FeedbackRequest request) {
		Optional<Quiz> quizOption = quizDao.findById(request.getQuizId());
		if (quizOption.isEmpty()) {
			return new FeedbackResponse(ResMessage.QUIZ_NOT_FOUND.getCode(), //
					ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		Quiz quiz = quizOption.get();
		List<Feedback> feedbackList = new ArrayList<>();
		try {
			// �����P�@���ݨ����^�X
			List<Response> responseList = responseDao.findByQuizID(request.getQuizId());
			// �M�� responseList
			for (Response responseItem : responseList) {
				List<Fillin> fillinList = mapper.readValue(responseItem.getFillin(), new TypeReference<>() {
				});
				FeedbackDetail detail = new FeedbackDetail(quiz.getName(), quiz.getDescriptions(), //
						quiz.getStartDate(), quiz.getEndDate(), responseItem.getName(), responseItem.getPhone(), //
						responseItem.getEmail(), responseItem.getAge(), fillinList);
				Feedback feedback = new Feedback(responseItem.getName(), responseItem.getFillinDateTime(), //
						detail);
				feedbackList.add(feedback);
			}
		} catch (Exception e) {
			return new FeedbackResponse(ResMessage.JSON_PROCESSING_EXCEPTION_ERROR.getCode(),
					ResMessage.JSON_PROCESSING_EXCEPTION_ERROR.getMessage());
		}
		return new FeedbackResponse(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), feedbackList);
	}

	@Override
	public StatisticsResponse Statistics(FeedbackRequest request) {
		List<Response> responseList = responseDao.findByQuizID(request.getQuizId());
		// �p��Ҧ��^�����A�D���B�ﶵ�B����
//		   qId(�D��)       �ﶵ     ����
		Map<Integer, Map<String, Integer>> countMap = new HashMap<>();
		for (Response item : responseList) {
			String fillinString = item.getFillin();
			try {
				List<Fillin> fillinList = mapper.readValue(fillinString, new TypeReference<>() {
				});
				for (Fillin fillin : fillinList) {
					Map<String, Integer> optionCountMap = new HashMap<>();
					// �ư�²���D:���C�J�έp
					if (fillin.getType().equalsIgnoreCase(OptionType.TEXT.getType())) {
						continue;
					}
					// �C�ӿﶵ�����O�Τ���(;)�걵
					String optionString = fillin.getOptions();
					String[] optionArray = optionString.split(";");
					String answer = fillin.getAnswer();
					answer = ";" + answer + ";"; // �z�ѦP�U

					for (String option : optionArray) { // optionArray: ����A������K�A�������K�A���l���
						// ��ﵪ�פ��C�ӿﶵ�X�{������
						// �קK�Y�ӿ�լO�t�@�ӿﶵ���䤤�@����
						// �Ҧp�G����F������K�F���l��� ���O�ﶵ�A�n��X����X�{���ơA������K�M���l����������
						// �ҥH�ݭn�b�C�ӿﶵ""�e��""�[�W�����A�|�Τ����O�]�����ת���ϥΤ���
						// ����n��X�{���ƮɡA�N�|�O�� ;���; �ӧ�
						String newOption = ";" + option + ";";
						// �z�L�N�ﶵ�Q�ťը��N�A�o�˥i�H�p��X��֪�����
						String newAnswerString = answer.replace(newOption, "");
						// �p��ӿﶵ�X�{�����Ʀ���
						// �쥻�r����� - �Q���N��r����סA�n���H�ﶵ�����פ~�|�O�u��������
						int count = (answer.length() - newAnswerString.length()) / newOption.length();
						// �O���C�@�D���έp
						optionCountMap = countMap.getOrDefault(fillin.getQuestionId(), optionCountMap);
						// �����X�ﶵ(key)����������
						// getOrDefault(option, 0): map ���S�� option(key) ���ܡA�N�|��^ 0
						int oldCount = optionCountMap.getOrDefault(option, 0);
						// �֥[ oldCount + count
						optionCountMap.put(option, oldCount + count);
						// �⦳�֥[���ƪ� optionCountMap �л\�^ countMap ��(�ۦP�D��)
						countMap.put(fillin.getQuestionId(), optionCountMap);
//						Statistics statistics = new Statistics(fillin.getQuestionId(), fillin.getQuestion(), //
//								fillin.isNecessary(), option, count);
					}
				}
			} catch (JsonProcessingException e) {
				return new StatisticsResponse(ResMessage.JSON_PROCESSING_EXCEPTION_ERROR.getCode(), //
						ResMessage.JSON_PROCESSING_EXCEPTION_ERROR.getMessage());
			}
		}
		Optional<Quiz> op = quizDao.findById(request.getQuizId());
		if (op.isEmpty()) {
			return new StatisticsResponse(ResMessage.QUIZ_NOT_FOUND.getCode(), //
					ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		Quiz quiz = op.get();
		return new StatisticsResponse(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), quiz.getName(), quiz.getStartDate(), quiz.getEndDate(), countMap);
	}
}
