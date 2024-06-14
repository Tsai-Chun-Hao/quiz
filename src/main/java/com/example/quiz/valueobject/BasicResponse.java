package com.example.quiz.valueobject;

public class BasicResponse {
	
	private int statusCode;

	private String message;
	
	// 預設建構方法
	public BasicResponse() {
		super();
	}
	// 帶有參數的建構法
	public BasicResponse(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	// 產生 get 和 set
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
