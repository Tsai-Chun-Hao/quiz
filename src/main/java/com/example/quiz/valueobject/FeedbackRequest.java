package com.example.quiz.valueobject;

public class FeedbackRequest {

	private int quizId;

	// 箇砞篶よ猭(⊿Τ把计)倒 Springboot ノ
	public FeedbackRequest() {
		super();
	}

	// 虫じ代刚穦ノ┮穝糤 "盿┮Τ把计篶よ猭"
	// 璶Τ盿Τ把计篶碞璶Τ箇砞篶よ猭
	public FeedbackRequest(int quizId) {
		super();
		this.quizId = quizId;
	}

	// 穝糤 get
	public int getQuizId() {
		return quizId;
	}

}
