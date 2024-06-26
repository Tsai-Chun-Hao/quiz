package com.example.quiz.valueobject;

import java.util.List;

import com.example.quiz.entity.Quiz;
import com.example.quiz.entity.Response;

public class FeedbackResponse extends BasicResponse {

	private List<Feedback> feedbackList;
	
	// 建構方法
	public FeedbackResponse() {
		super();
	}
	
	// 帶有參數的建構方法
	public FeedbackResponse(int statusCode, String message) {
		super(statusCode, message);
	}

	// 帶有參數的建構方法
	public FeedbackResponse(int statusCode, String message, List<Feedback> feedbackList) {
		super(statusCode, message);
		this.feedbackList = feedbackList;
	}
	
	// 新增 get
	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}
}
