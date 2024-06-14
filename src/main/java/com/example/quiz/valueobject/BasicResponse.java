package com.example.quiz.valueobject;

public class BasicResponse {
	
	private int statusCode;

	private String message;
	
	// �w�]�غc��k
	public BasicResponse() {
		super();
	}
	// �a���Ѽƪ��غc�k
	public BasicResponse(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	// ���� get �M set
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
