package com.example.quiz.valueobject;

import java.time.LocalDateTime;

public class Feedback {
	
	// �s�������n�g
//	private int responseId;
	
	private String userName;
	
	// ��g�ɶ�
	private LocalDateTime fillinDateTime;
	
	private FeedbackDetail feedbackDetail;
	
	// �غc��k
	public Feedback() {
		super();
	}
	
	// �a���Ѽƪ��غc��k
	public Feedback(String userName, LocalDateTime fillinDateTime, FeedbackDetail feedbackDetail) {
		super();
		this.userName = userName;
		this.fillinDateTime = fillinDateTime;
		this.feedbackDetail = feedbackDetail;
	}
	
	// �s�W get
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
