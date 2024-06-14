package com.example.quiz.valueobject;

import java.util.List;

import com.example.quiz.entity.Quiz;
import com.example.quiz.entity.Response;

public class FeedbackResponse extends BasicResponse {

	private List<Feedback> feedbackList;
	
	// 篶よ猭
	public FeedbackResponse() {
		super();
	}
	
	// 盿Τ把计篶よ猭
	public FeedbackResponse(int statusCode, String message) {
		super(statusCode, message);
	}

	// 盿Τ把计篶よ猭
	public FeedbackResponse(int statusCode, String message, List<Feedback> feedbackList) {
		super(statusCode, message);
		this.feedbackList = feedbackList;
	}
	
	// 穝糤 get
	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}
}
