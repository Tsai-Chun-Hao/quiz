package com.example.quiz.constants;

// enum 是 "列舉"
public enum ResMessage {
	// 需要用到代碼 200
	// enum 不能 new ， SUCCESS(200, "Success!!") 是 enum 的格式
	// enum 通常用在"下拉是選單"，放在後端可以增刪，放在前端 hard code(寫死)
	SUCCESS(200, "Success!!"), //
	PARAM_QUZI_NAME_ERROR(400, "Param quiz name error!!"), //
	PARAM_DESCRIPTION_ERROR(400, "Param description error!!"), //
	PARAM_START_DATE_ERROR(400, "Param start date error!!"), //
	PARAM_END_TDATE_ERROR(400, "Param end date error!!"), //
	// NOT FOUND 找不到是 404
	PARAM_QUESTION_LIST_NOT_FOUND(404, "Param question list not found!!"), //
	PARAM_QUESTION_ID_ERROR(400, "Param question id error!!"), //
	PARAM_QUIZ_ID_ERROR(400, "Param quiz id error!!"), //
	PARAM_TITLE_ERROR(400, "Param title error!!"), //
	PARAM_OPTIONS_ERROR(400, "Param options error!!"), //
	PARAM_TYPE_ERROR(400, "Param type error!!"), //
	JSON_PROCESSING_EXCEPTION_ERROR(400, "json processing exception error!!"),//
	// NOT FOUND 找不到是 404
	UPDATE_ID_NOT_FOUND(404, "update id not found!!"), //
	PARAM_NAME_IS_REQUIRED(400, "Param name is required!!"), //
	PARAM_PHONE_IS_REQUIRED(400, "Param phone is required!!"), //
	PARAM_EMAIL_IS_REQUIRED(400, "Param email is required!!"), //
	PARAM_AGE_NOT_QUALIFIED(400, "Param age not qualified!!"), //
	QUIZ_NOT_FOUND(404, "quiz not found!!"), //
	ANSWER_IS_REQUIRED(400, "answer is required!!"), //
	ANSWER_OPTIONS_IS_NOT_MATCH(400, "answer options is not match!!"), //
	ANSWER_OPTION_TYPE_IS_NOT_MATCH(400, "answer option_type is not match!!"), //
	DUPLICATED_FILLIN(400, "Duplicated fillin!!");
	
	// 有些人不使用 code
	private int code;
	
	private String message;
	
	// 帶有參數的建構方法，如果刪除帶有參數的建構方法，SUCCESS 會紅蚯蚓
	private ResMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	// enum 只會使用到 get
	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	
}
