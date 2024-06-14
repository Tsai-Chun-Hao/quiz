package com.example.quiz.valueobject;

import java.time.LocalDateTime;

public class Feedback {
	
	// 編號先不要寫
//	private int responseId;
	
	private String userName;
	
	// 填寫時間
	private LocalDateTime fillinDateTime;
	
	private FeedbackDetail feedbackDetail;
	
	// 建構方法
	public Feedback() {
		super();
	}
	
	// 帶有參數的建構方法
	public Feedback(String userName, LocalDateTime fillinDateTime, FeedbackDetail feedbackDetail) {
		super();
		this.userName = userName;
		this.fillinDateTime = fillinDateTime;
		this.feedbackDetail = feedbackDetail;
	}
	
	// 新增 get
	public String getUserName() {
		return userName;
	}

	public LocalDateTime getFillinDateTime() {
		return fillinDateTime;
	}

	public FeedbackDetail getFeedbackDetail() {
		return feedbackDetail;
	}		
}
