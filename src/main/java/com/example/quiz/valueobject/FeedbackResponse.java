package com.example.quiz.valueobject;

import java.util.List;

import com.example.quiz.entity.Quiz;
import com.example.quiz.entity.Response;

public class FeedbackResponse extends BasicResponse {

	private List<Feedback> feedbackList;
	
	// �غc��k
	public FeedbackResponse() {
		super();
	}
	
	// �a���Ѽƪ��غc��k
	public FeedbackResponse(int statusCode, String message) {
		super(statusCode, message);
	}

	// �a���Ѽƪ��غc��k
	public FeedbackResponse(int statusCode, String message, List<Feedback> feedbackList) {
		super(statusCode, message);
		this.feedbackList = feedbackList;
	}
	
	// �s�W get
	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}
}
