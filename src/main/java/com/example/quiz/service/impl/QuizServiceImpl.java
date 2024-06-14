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
		// 檢查參數
		BasicResponse checkResult = checkParams(request);
		// createResult == null 時，表示參數檢查都正確
		if (checkResult != null) {
			return checkResult;
		}
		// 因為 Quiz 中的 questions 的資料格式是 String ，所以要將 request 裡面的 List<Question> 轉成 String
		// 透過 ObjectMapper 可以把物件(類別)轉成 JSON 格式字串
		ObjectMapper mapper = new ObjectMapper();
		try {
			// 把 QuestionList 轉成字串 String
			String questionsString = mapper.writeValueAsString(request.getQuestionList());

			// 若 request 中的 id > 0，表示 "更新" 已存在的資料: 若 id = 0; 則表示要新增
			if (request.getId() > 0) {
				// 以下2種方式擇一
				// 使用方法1 ， 透過 findById ，若有資料，就會回傳一整筆的資料(可能資料量會較大)
				// 使用方法2 ， 因為是透過 existsById 來判斷資料是否存在，所以回傳的資料永遠都會是一個 bit( 0 或 1 )

				// 方法 1. 透過 findById: 若有資料，回傳整筆資料
				// findbyid 會是被 Optional 包起來的物件
//				Optional<Quiz> op = quizDao.findById(request.getId());				
				// 判斷是否有資料
//				if (op.isEmpty()) { // op.isEmpty(): 表示沒資料
//					return new BasicResponse(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), //
//							ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
//				}
//				Quiz quiz = op.get();
				// 設定新值(值從 request 來)
				// 將 request 中的新值設定到舊的 quiz 中，不設定 id ，因為 id 一樣
//				quiz.setName(request.getName());
//				quiz.setDescriptions(request.getDescriptions());
//				quiz.setStartDate(request.getStartDate());
//				quiz.setEndDate(request.getEndDate());
				// Questions 不是從 request 裡面來的
//				quiz.setQuestions(questionsString);
//				quiz.setPublished(request.isPublished());

				// 方法 2. 透過 existsById: 回傳一個 bit 的值
				// 這邊要判斷從 request 帶進來的 id 是否是真的有存在 DB 中
				// 因為若 id 不存在，後續程式碼再呼叫 jpa的 save 方法時，會變成新增
				boolean boo = quizDao.existsById(request.getId());
				if (!boo) { // !boo 表示資料不存在
					return new BasicResponse(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), //
							ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
				}
				// 要把 id 放進去
//				quiz.setId(request.getId());

				// 因為是 new 一個新的 Quiz，所以要把 Id 放進去
//				Quiz quiz = new Quiz(request.getId(), request.getName(), request.getDescriptions(), //
//						request.getStartDate(), request.getEndDate(), //
//						questionsString, request.isPublished());
//				System.out.println("================================================================");
			}

			// ========================================================================================
			// 上述一整段的 if 程式碼可以縮減成以下這一段程式碼
//			if (request.getId() > 0 && !quizDao.existsById(request.getId())) {
//				return new BasicResponse(ResMessage.UPDATE_ID_NOT_FOUND.getCode(), //
//						ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
//			}
			// ========================================================================================

			// 因為變數 quiz 只使用一次，因此可以使用匿名方式(匿名類別)撰寫
			// 最後的目的都會去 save Quiz
			// new Quiz()中帶入 request.getId() 是 PK，在呼叫save時，會先去檢查 PK 是否存在於 DB 中，
			// 若存在 --> 更新：不存在 --> 新增
			// Request 中沒有該欄位時，預設是 0 ，因為 id 的資料型態是 int
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

	// 抽出來的時機
	// 1. 一個區塊不影響整個閱讀的時候，可以把它抽出來
	// 2. 重複的區塊抽出來變私有的方法
	private BasicResponse checkParams(CreateOrUpdateRequest request) {
		// 檢查 "問卷" 參數
		// StringUtils.hasText(字串)：會檢查字串是否為 null、空字串、全空白字串，若是符合3種中其中一項，會回false
		// 前面加一個驚嘆號表示反向的意思，如果字串檢查結果是 false 就會進到 if 的實作區塊
		// !StringUtils.hasText(request.getName()) 等同於
		// StringUtils.hasText(request.getName()) == false
		// 有驚嘆號 !
		if (!StringUtils.hasText(request.getName())) {
			return new BasicResponse(ResMessage.PARAM_QUZI_NAME_ERROR.getCode(), //
					ResMessage.PARAM_QUZI_NAME_ERROR.getMessage());
		}
		if (!StringUtils.hasText(request.getDescriptions())) {
			return new BasicResponse(ResMessage.PARAM_DESCRIPTION_ERROR.getCode(), //
					ResMessage.PARAM_DESCRIPTION_ERROR.getMessage());
		}

//		方法一
//		LocalDate.now()) 是取得系統當前時間
//		request.getStartDate().isBefore(LocalDate.now()): 若 request
//		中的開始時間"早"於當前時間，會得到 true
//		request.getStartDate().isEqual(LocalDate.now()): 若 request 中的開始時間"等"於當前時間，會得到
//		true
//		因為開始時間不能在今天(包含)之前，所以上兩個比較若是任一個結果為 true，則表示開始時間要比當前(包含)時間早

//		if (request.getStartDate() == null || request.getStartDate().isBefore(LocalDate.now()) //
//				|| request.getStartDate().isEqual(LocalDate.now())) {
//			return new BasicResponse(ResMessage.PARAM_START_DATE_ERROR.getCode(), //
//					ResMessage.PARAM_START_DATE_ERROR.getMessage());
//		}

		// 方法二
		// 1."開始" 時間不能小於當前時間
		// request.getStartDate().isAfter(LocalDate.now()): 若 request 中的開始時間比當前時間晚，會得到
		// true
		// !request.getStartDate().isAfter(LocalDate.now()): 前面有加驚嘆號，表示會得到相反的結果，就是開始時間
		// 會小於等於當前時間
		if (request.getStartDate() == null || !request.getStartDate().isAfter(LocalDate.now())) {
			return new BasicResponse(ResMessage.PARAM_START_DATE_ERROR.getCode(), //
					ResMessage.PARAM_START_DATE_ERROR.getMessage());
		}

//                                                                  開始(true)--------結束(true)
//                                     當前LocalDate.now()------------------------------------>

//!request.getStartDate().isAfter(LocalDate.now())
// <-----------------------------------開始(false)

//!request.getEndDate().isAfter(LocalDate.now())
// <-----------------------------------結束(false)

		// 程式碼有執行到這行時，表示 "開始" 時間一定大於等於當前時間
		// 所以後續檢查結束時間時，只要確定 "結束" 時間是大於等於 "開始" 時間即可，因為只要結束時間是大於等於開始時間，
		// 就一定會是大於等於當前時間
		// 開始時間 >= 當前時間 : 結束時間 >= 開始時間 ==> 結束時間 >= 開始時間 >= 當前時間
		// 所以不需要判斷 !request.getEndDate().isAfter(LocalDate.now()
		// 1. "結束" 時間不能"小於等於"當前時間 2. "結束" 時間不能"小於"開始時間
		if (request.getEndDate() == null || request.getEndDate().isBefore(request.getStartDate())) {
			return new BasicResponse(ResMessage.PARAM_END_TDATE_ERROR.getCode(), //
					ResMessage.PARAM_END_TDATE_ERROR.getMessage());
		}

		// 檢查 "問題" 參數
		// 先檢查 QuestionList
		if (CollectionUtils.isEmpty(request.getQuestionList())) {
			return new BasicResponse(ResMessage.PARAM_QUESTION_LIST_NOT_FOUND.getCode(), //
					ResMessage.PARAM_QUESTION_LIST_NOT_FOUND.getMessage());
		}
		// 一張問卷可能有多個問題，所以要逐筆檢查每一筆的參數
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
			// 當 option_type 是單選或多選時，options 就不能是空字串
			// 但 option_type 是文字時，options 允許是空字串
			// 以下條件檢查: 當 option_type 是單選或多選時，且 options 是空字串，返回錯誤
			if (item.getType().equals(OptionType.SINGLE_CHOICE.getType())
					|| item.getType().equals(OptionType.MULTI_CHOICE.getType())) {
				// 又是空的
				if (!StringUtils.hasText(item.getOptions())) {
					return new BasicResponse(ResMessage.PARAM_OPTIONS_ERROR.getCode(), //
							ResMessage.PARAM_OPTIONS_ERROR.getMessage());
				}
			}
			// 以下是上述2個 if 合併寫法: (條件1 || 條件2) && 條件3
			// 第一個 if 條件式 && 第二個 if 條件式
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
		// 假設 name 是 null 或是全空白字串，可以視為沒有輸入條件值，就是表示要取得全部
		// JPA 的 containing 的方法，條件值是空字串時，會搜尋全部
		// 所以要把 name 的值是 null 或全空白字串時，轉換成空字串
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
		// 檢查參數
		// list 是屬於集合，是 Collection 的一種
		// 假設 isEmpty 不是空的話，就刪除問卷
		if (!CollectionUtils.isEmpty(request.getIdList())) {
			// 刪除問卷
			try {
				quizDao.deleteAllById(request.getIdList());
			} catch (Exception e) {
				// 當 deleteAllyById 方法中，id 的值不存在時，JPA 會報錯
				// 因為在刪除之前， JPA 會先搜尋帶入的 id 值，若沒結果就會報錯
				// 由於實際上也沒刪除任何東西，因此就不需要對這個 Exception 做處理
			}
		}
		// 如果 IdList 是空的就不會執行刪除問卷，就會執行 SUCCESS 成功
		return new BasicResponse(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());

//		程式碼能不連接 "資料庫" 就不要連接
//		quizDao.deleteAllById(request.getIdList());
//		return new BasicResponse(ResMessage.SUCCESS.getCode(), //
//				ResMessage.SUCCESS.getMessage());
	}

}
