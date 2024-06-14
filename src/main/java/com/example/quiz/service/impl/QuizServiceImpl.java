package com.example.quiz.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.OptionType;
import com.example.quiz.constants.ResMessage;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.valueobject.BasicResponse;
import com.example.quiz.valueobject.CreateOrUpdateRequest;
import com.example.quiz.valueobject.DeleteRequest;
import com.example.quiz.valueobject.Question;
import com.example.quiz.valueobject.SearchRequest;
import com.example.quiz.valueobject.SearchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizDao;

	@Override
	public BasicResponse createOrUpdate(CreateOrUpdateRequest request) {
		// �ˬd�Ѽ�
		BasicResponse checkResult = checkParams(request);
		// createResult == null �ɡA��ܰѼ��ˬd�����T
		if (checkResult != null) {
			return checkResult;
		}
		// �]�� Quiz ���� questions ����Ʈ榡�O String �A�ҥH�n�N request �̭��� List<Question> �ন String
		// �z�L ObjectMapper �i�H�⪫��(���O)�ন JSON �榡�r��
		ObjectMapper mapper = new ObjectMapper();
		try {
			// �� QuestionList �ন�r�� String
			String questionsString = mapper.writeValueAsString(request.getQuestionList());

			// �Y request ���� id > 0�A��� "��s" �w�s�b�����: �Y id = 0; �h��ܭn�s�W
			if (request.getId() > 0) {
				// �H�U2�ؤ覡�ܤ@
				// �ϥΤ�k1 �A �z�L findById �A�Y����ơA�N�|�^�Ǥ@�㵧�����(�i���ƶq�|���j)
				// �ϥΤ�k2 �A �]���O�z�L existsById �ӧP�_��ƬO�_�s�b�A�ҥH�^�Ǫ���ƥû����|�O�@�� bit( 0 �� 1 )

				// ��k 1. �z�L findById: �Y����ơA�^�Ǿ㵧���
				// findbyid �|�O�Q Optional �]�_�Ӫ�����
//				Optional<Quiz> op = quizDao.findById(request.getId());				
				// �P�_�O�_�����
//				if (op.isEmpty()) { // op.isEmpty(): ��ܨS���
//					return new BasicResponse(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), //
//							ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
//				}
//				Quiz quiz = op.get();
				// �]�w�s��(�ȱq request ��)
				// �N request �����s�ȳ]�w���ª� quiz ���A���]�w id �A�]�� id �@��
//				quiz.setName(request.getName());
//				quiz.setDescriptions(request.getDescriptions());
//				quiz.setStartDate(request.getStartDate());
//				quiz.setEndDate(request.getEndDate());
				// Questions ���O�q request �̭��Ӫ�
//				quiz.setQuestions(questionsString);
//				quiz.setPublished(request.isPublished());

				// ��k 2. �z�L existsById: �^�Ǥ@�� bit ����
				// �o��n�P�_�q request �a�i�Ӫ� id �O�_�O�u�����s�b DB ��
				// �]���Y id ���s�b�A����{���X�A�I�s jpa�� save ��k�ɡA�|�ܦ��s�W
				boolean boo = quizDao.existsById(request.getId());
				if (!boo) { // !boo ��ܸ�Ƥ��s�b
					return new BasicResponse(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), //
							ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
				}
				// �n�� id ��i�h
//				quiz.setId(request.getId());

				// �]���O new �@�ӷs�� Quiz�A�ҥH�n�� Id ��i�h
//				Quiz quiz = new Quiz(request.getId(), request.getName(), request.getDescriptions(), //
//						request.getStartDate(), request.getEndDate(), //
//						questionsString, request.isPublished());
//				System.out.println("================================================================");
			}

			// ========================================================================================
			// �W�z�@��q�� if �{���X�i�H�Y��H�U�o�@�q�{���X
//			if (request.getId() > 0 && !quizDao.existsById(request.getId())) {
//				return new BasicResponse(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), //
//						ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
//			}
			// ========================================================================================

			// �]���ܼ� quiz �u�ϥΤ@���A�]���i�H�ϥΰΦW�覡(�ΦW���O)���g
			// �̫᪺�ت����|�h save Quiz
			// new Quiz()���a�J request.getId() �O PK�A�b�I�ssave�ɡA�|���h�ˬd PK �O�_�s�b�� DB ���A
			// �Y�s�b --> ��s�G���s�b --> �s�W
			// Request ���S�������ɡA�w�]�O 0 �A�]�� id ����ƫ��A�O int
			quizDao.save(new Quiz(request.getId(), request.getName(), request.getDescriptions(), request.getStartDate(),
					request.getEndDate(), questionsString, request.isPublished()));

//			quizDao.save(quiz);

		} catch (JsonProcessingException e) {
			return new BasicResponse(ResMessage.JSON_PROCESSING_EXCEPTION_ERROR.getCode(), //
					ResMessage.JSON_PROCESSING_EXCEPTION_ERROR.getMessage());
		}

// ======================================================
//		if(checkResult.getStatusCode() != 200)	{
//			
//		}
		return new BasicResponse(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());
//		return null;
	}

	// ��X�Ӫ��ɾ�
	// 1. �@�Ӱ϶����v�T��Ӿ\Ū���ɭԡA�i�H�⥦��X��
	// 2. ���ƪ��϶���X���ܨp������k
	private BasicResponse checkParams(CreateOrUpdateRequest request) {
		// �ˬd "�ݨ�" �Ѽ�
		// StringUtils.hasText(�r��)�G�|�ˬd�r��O�_�� null�B�Ŧr��B���ťզr��A�Y�O�ŦX3�ؤ��䤤�@���A�|�^false
		// �e���[�@����ĸ���ܤϦV���N��A�p�G�r���ˬd���G�O false �N�|�i�� if ����@�϶�
		// !StringUtils.hasText(request.getName()) ���P��
		// StringUtils.hasText(request.getName()) == false
		// ����ĸ� !
		if (!StringUtils.hasText(request.getName())) {
			return new BasicResponse(ResMessage.PARAM_QUZI_NAME_ERROR.getCode(), //
					ResMessage.PARAM_QUZI_NAME_ERROR.getMessage());
		}
		if (!StringUtils.hasText(request.getDescriptions())) {
			return new BasicResponse(ResMessage.PARAM_DESCRIPTION_ERROR.getCode(), //
					ResMessage.PARAM_DESCRIPTION_ERROR.getMessage());
		}

//		��k�@
//		LocalDate.now()) �O���o�t�η�e�ɶ�
//		request.getStartDate().isBefore(LocalDate.now()): �Y request
//		�����}�l�ɶ�"��"���e�ɶ��A�|�o�� true
//		request.getStartDate().isEqual(LocalDate.now()): �Y request �����}�l�ɶ�"��"���e�ɶ��A�|�o��
//		true
//		�]���}�l�ɶ�����b����(�]�t)���e�A�ҥH�W��Ӥ���Y�O���@�ӵ��G�� true�A�h��ܶ}�l�ɶ��n���e(�]�t)�ɶ���

//		if (request.getStartDate() == null || request.getStartDate().isBefore(LocalDate.now()) //
//				|| request.getStartDate().isEqual(LocalDate.now())) {
//			return new BasicResponse(ResMessage.PARAM_START_DATE_ERROR.getCode(), //
//					ResMessage.PARAM_START_DATE_ERROR.getMessage());
//		}

		// ��k�G
		// 1."�}�l" �ɶ�����p���e�ɶ�
		// request.getStartDate().isAfter(LocalDate.now()): �Y request �����}�l�ɶ����e�ɶ��ߡA�|�o��
		// true
		// !request.getStartDate().isAfter(LocalDate.now()): �e�����[��ĸ��A��ܷ|�o��ۤϪ����G�A�N�O�}�l�ɶ�
		// �|�p�󵥩��e�ɶ�
		if (request.getStartDate() == null || !request.getStartDate().isAfter(LocalDate.now())) {
			return new BasicResponse(ResMessage.PARAM_START_DATE_ERROR.getCode(), //
					ResMessage.PARAM_START_DATE_ERROR.getMessage());
		}

//                                                                  �}�l(true)--------����(true)
//                                     ��eLocalDate.now()------------------------------------>

//!request.getStartDate().isAfter(LocalDate.now())
// <-----------------------------------�}�l(false)

//!request.getEndDate().isAfter(LocalDate.now())
// <-----------------------------------����(false)

		// �{���X�������o��ɡA��� "�}�l" �ɶ��@�w�j�󵥩��e�ɶ�
		// �ҥH�����ˬd�����ɶ��ɡA�u�n�T�w "����" �ɶ��O�j�󵥩� "�}�l" �ɶ��Y�i�A�]���u�n�����ɶ��O�j�󵥩�}�l�ɶ��A
		// �N�@�w�|�O�j�󵥩��e�ɶ�
		// �}�l�ɶ� >= ��e�ɶ� : �����ɶ� >= �}�l�ɶ� ==> �����ɶ� >= �}�l�ɶ� >= ��e�ɶ�
		// �ҥH���ݭn�P�_ !request.getEndDate().isAfter(LocalDate.now()
		// 1. "����" �ɶ�����"�p�󵥩�"��e�ɶ� 2. "����" �ɶ�����"�p��"�}�l�ɶ�
		if (request.getEndDate() == null || request.getEndDate().isBefore(request.getStartDate())) {
			return new BasicResponse(ResMessage.PARAM_END_TDATE_ERROR.getCode(), //
					ResMessage.PARAM_END_TDATE_ERROR.getMessage());
		}

		// �ˬd "���D" �Ѽ�
		// ���ˬd QuestionList
		if (CollectionUtils.isEmpty(request.getQuestionList())) {
			return new BasicResponse(ResMessage.PARAM_QUESTION_LIST_NOT_FOUND.getCode(), //
					ResMessage.PARAM_QUESTION_LIST_NOT_FOUND.getMessage());
		}
		// �@�i�ݨ��i�঳�h�Ӱ��D�A�ҥH�n�v���ˬd�C�@�����Ѽ�
		for (Question item : request.getQuestionList()) {
			if (item.getId() <= 0) {
				return new BasicResponse(ResMessage.PARAM_QUESTION_ID_ERROR.getCode(), //
						ResMessage.PARAM_QUESTION_ID_ERROR.getMessage());
			}
			if (!StringUtils.hasText(item.getTitle())) {
				return new BasicResponse(ResMessage.PARAM_TITLE_ERROR.getCode(), //
						ResMessage.PARAM_TITLE_ERROR.getMessage());
			}
			if (!StringUtils.hasText(item.getType())) {
				return new BasicResponse(ResMessage.PARAM_TYPE_ERROR.getCode(), //
						ResMessage.PARAM_TYPE_ERROR.getMessage());
			}
			// �� option_type �O���Φh��ɡAoptions �N����O�Ŧr��
			// �� option_type �O��r�ɡAoptions ���\�O�Ŧr��
			// �H�U�����ˬd: �� option_type �O���Φh��ɡA�B options �O�Ŧr��A��^���~
			if (item.getType().equals(OptionType.SINGLE_CHOICE.getType())
					|| item.getType().equals(OptionType.MULTI_CHOICE.getType())) {
				// �S�O�Ū�
				if (!StringUtils.hasText(item.getOptions())) {
					return new BasicResponse(ResMessage.PARAM_OPTIONS_ERROR.getCode(), //
							ResMessage.PARAM_OPTIONS_ERROR.getMessage());
				}
			}
			// �H�U�O�W�z2�� if �X�ּg�k: (����1 || ����2) && ����3
			// �Ĥ@�� if ���� && �ĤG�� if ����
//			if ((item.getType().equals(OptionType.SINGLE_CHOICE.getType())
//					|| item.getType().equals(OptionType.MULTI_CHOICE.getType()))
//					&& !StringUtils.hasText(item.getOptions())) {
//				return new BasicResponse(ResMessage.PARAM_OPTIONS_ERROR.getCode(), //
//						ResMessage.PARAM_OPTIONS_ERROR.getMessage());
//			}

		}
		return null;
//		return new BasicResponse(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());

	}
//	if (!StringUtils.hasText(request.getName()) || !StringUtils.hasText(request.getDescriptions())
//		|| request.getStartDate() == null || request.getEndDate() == null || request.getQuestionId() <= 0
//		|| !StringUtils.hasText(request.getContent()) || !StringUtils.hasText(request.getType())) {
//	}

	@Override
	public SearchResponse search(SearchRequest request) {
		String name = request.getName();
		LocalDate start = request.getStartDate();
		LocalDate end = request.getEndDate();
		// ���] name �O null �άO���ťզr��A�i�H�����S����J����ȡA�N�O��ܭn���o����
		// JPA �� containing ����k�A����ȬO�Ŧr��ɡA�|�j�M����
		// �ҥH�n�� name ���ȬO null �Υ��ťզr��ɡA�ഫ���Ŧr��
		if (!StringUtils.hasText(name)) {
			name = "";
		}
		if (start == null) {
			start = LocalDate.of(1970, 1, 1);
		}
		if (end == null) {
			end = LocalDate.of(2999, 12, 31);
		}
//		List<Quiz> response = quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(name, start, end);
//		return new SearchResponse(ResMessage.SUCCESS.getCode(),ResMessage.SUCCESS.getMessage(), response);
		return new SearchResponse(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), //
				quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(name, start, end));
//		return null;
	}

	@Override
	public BasicResponse delete(DeleteRequest request) {
		// �ˬd�Ѽ�
		// list �O�ݩ󶰦X�A�O Collection ���@��
		// ���] isEmpty ���O�Ū��ܡA�N�R���ݨ�
		if (!CollectionUtils.isEmpty(request.getIdList())) {
			// �R���ݨ�
			try {
				quizDao.deleteAllById(request.getIdList());
			} catch (Exception e) {
				// �� deleteAllyById ��k���Aid ���Ȥ��s�b�ɡAJPA �|����
				// �]���b�R�����e�A JPA �|���j�M�a�J�� id �ȡA�Y�S���G�N�|����
				// �ѩ��ڤW�]�S�R������F��A�]���N���ݭn��o�� Exception ���B�z
			}
		}
		// �p�G IdList �O�Ū��N���|����R���ݨ��A�N�|���� SUCCESS ���\
		return new BasicResponse(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());

//		�{���X�ण�s�� "��Ʈw" �N���n�s��
//		quizDao.deleteAllById(request.getIdList());
//		return new BasicResponse(ResMessage.SUCCESS.getCode(), //
//				ResMessage.SUCCESS.getMessage());
	}

}
