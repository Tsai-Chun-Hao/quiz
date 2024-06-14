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
		// 參數檢查
		BasicResponse checkResult = checkParams(request);
		if (checkResult != null) {
			return checkResult;
		}
		// 檢查同一個電話號碼是否有重複填寫同一張問卷
		if (responseDao.existsByQuizIDAndPhone(request.getQuizId(), request.getPhone())) {
			return new BasicResponse(ResMessage.DUPLICATED_FILLIN.getCode(), //
					ResMessage.DUPLICATED_FILLIN.getMessage());
		}
		// 檢查 quiz_id 是否存在於DB中
		// 因為後續會比對 req 中的答案與題目的選項是否符合，所以要用 findById
		Optional<Quiz> op = quizDao.findById(request.getQuizId());
		if (op.isEmpty()) {
			return new BasicResponse(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		Quiz quiz = op.get();
		// 從 quiz 中取出 questions 字串
		String questionsStr = quiz.getQuestions();
		// 使用 ObjectMapper 將 questionsStr 轉成 List<Question>
		// fillinStr 要給空字串，不然預設會是 null
		// 若 fillinStr = null，後續執行 fillinStr =
		// mapper.writeValueAsString(req.getqIdAnswerMap());
		// 把執行得到的結果塞回給 fillinStr 時，會報錯
		String fillinStr = "";
		try {
			// 建立正確的 List<Fillin>
			List<Fillin> newFillinList = new ArrayList<>();
			// 建立已新增的 question_id List
			List<Integer> qIds = new ArrayList<>();
			List<Question> quList = mapper.readValue(questionsStr, new TypeReference<>() {
			});
			// 比對每一個 Question 與 fillin 中的答案
			for (Question item : quList) { // 1, 2, 3
				List<Fillin> fillinList = request.getFillinList();
				for (Fillin fillin : fillinList) { // 1, 3
					// id 不一致，跳過
					if (item.getId() != fillin.getQuestionId()) {
						continue;
					}
					// 如果 qIds 已經有包含問題編號，表示已檢查過該題號
					// 此段用來排除 request 中有重複的問題編號
					if (qIds.contains(fillin.getQuestionId())) {
						continue;
					}
					// 將已新增問題之題號加入
					qIds.add(fillin.getQuestionId()); // 1, 3
					// 新增相同題號的 fillin
					// 不直接把 fillin 加到 list 的原因是：
					// 上面的程式碼除了對 question_id 和 answer 有檢查，所以其餘的屬性內容可能是不合法的
					// 直接使用 Question item 的值是因為這些值都是從 DB 來，當初已有檢查過
					newFillinList.add(new Fillin(item.getId(), item.getTitle(), //
							item.getOptions(), fillin.getAnswer(), item.getType(), //
							item.isNecessary()));
					// 檢查選項與答案
					checkResult = checkOptionAnswer(item, fillin);
					if (checkResult != null) {
						return checkResult;
					}
				}
				// 正常的情況是: 問題是必填，然後又有回答，每跑完一題，qIds 就會包含該必填的問題的 id
				// 因此若問題是必填，但 qId 又沒有包含該題的 id，就表示沒有回答該題
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
		// 1. 檢查必填也要有答案
		// fillin 中的答案沒有值，返回錯誤
		if (item.isNecessary() && !StringUtils.hasText(fillin.getAnswer())) {
			return new BasicResponse(ResMessage.ANSWER_IS_REQUIRED.getCode(),
					ResMessage.ANSWER_IS_REQUIRED.getMessage());
		}
		// 2. 排除題型是單選 但 answerArray 的長度 > 1
		String answerStr = fillin.getAnswer();
		// 把 answerStr(答案) 切割成陣列
		String[] answerArray = answerStr.split(";");
		if (item.getType().equalsIgnoreCase(OptionType.SINGLE_CHOICE.getType()) //
				&& answerArray.length > 1) {
			return new BasicResponse(ResMessage.ANSWER_OPTION_TYPE_IS_NOT_MATCH.getCode(),
					ResMessage.ANSWER_OPTION_TYPE_IS_NOT_MATCH.getMessage());
		}
		// 3. 排除簡答題；option type 是 text(因為接下來要檢查選項與答案是否一致)
		if (item.getType().equalsIgnoreCase(OptionType.TEXT.getType())) {
			return null;
		}
		// 把 options 切成 Array
		String[] optionArray = item.getOptions().split(";");
		// 把 optionArray 轉成 List，因為要使用 List 中的 contains 方法
		List<String> optionList = List.of(optionArray);
		// 4. 檢查答案跟選項一致
		for (String str : answerArray) {
			// 假設 item.getOptions() 的值是: "AB;BC;C;D"
			// 轉成 List 後的 optionList = ["AB", "BC", "C", "D"]
			// 假設 answerArray = [AB, B]
			// for 迴圈中就是把 AB 和 B 比對是否被包含在 optionList 中
			// List 的 contains 方法是比較元素，所以範例中，AB是有包含，B是沒有
			// 排除以下:
			// 1. 必填 && 答案選項不一致
			if (item.isNecessary() && !optionList.contains(str)) {
				return new BasicResponse(ResMessage.ANSWER_OPTIONS_IS_NOT_MATCH.getCode(),
						ResMessage.ANSWER_OPTIONS_IS_NOT_MATCH.getMessage());
			}
			// 2. 非必填 && 有答案 && 答案選項不一致
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
			// 撈取同一份問卷的回饋
			List<Response> responseList = responseDao.findByQuizID(request.getQuizId());
			// 遍歷 responseList
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
		// 計算所有回答之，題號、選項、次數
//		   qId(題號)       選項     次數
		Map<Integer, Map<String, Integer>> countMap = new HashMap<>();
		for (Response item : responseList) {
			String fillinString = item.getFillin();
			try {
				List<Fillin> fillinList = mapper.readValue(fillinString, new TypeReference<>() {
				});
				for (Fillin fillin : fillinList) {
					Map<String, Integer> optionCountMap = new HashMap<>();
					// 排除簡答題:不列入統計
					if (fillin.getType().equalsIgnoreCase(OptionType.TEXT.getType())) {
						continue;
					}
					// 每個選項之間是用分號(;)串接
					String optionString = fillin.getOptions();
					String[] optionArray = optionString.split(";");
					String answer = fillin.getAnswer();
					answer = ";" + answer + ";"; // 理由同下

					for (String option : optionArray) { // optionArray: 綠茶，綠茶拿鐵，紅茶拿鐵，梅子綠茶
						// 比對答案中每個選項出現的次數
						// 避免某個選校是另一個選項的其中一部份
						// 例如：綠茶；綠茶拿鐵；梅子綠茶 都是選項，要找出綠茶出現次數，綠茶拿鐵和梅子綠茶都不能算
						// 所以需要在每個選項""前後""加上分號，會用分號是因為答案的街使用分號
						// 後續要找出現次數時，就會是用 ;綠茶; 來找
						String newOption = ";" + option + ";";
						// 透過將選項被空白取代，這樣可以計算出減少的長度
						String newAnswerString = answer.replace(newOption, "");
						// 計算該選項出現的次數次數
						// 原本字串長度 - 被取代後字串長度，要除以選項的長度才會是真正的次數
						int count = (answer.length() - newAnswerString.length()) / newOption.length();
						// 記錄每一題的統計
						optionCountMap = countMap.getOrDefault(fillin.getQuestionId(), optionCountMap);
						// 先取出選項(key)對應的次數
						// getOrDefault(option, 0): map 中沒有 option(key) 的話，就會返回 0
						int oldCount = optionCountMap.getOrDefault(option, 0);
						// 累加 oldCount + count
						optionCountMap.put(option, oldCount + count);
						// 把有累加次數的 optionCountMap 覆蓋回 countMap 中(相同題號)
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
