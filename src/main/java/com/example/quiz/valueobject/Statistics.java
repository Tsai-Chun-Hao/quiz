package com.example.quiz.valueobject;

public class Statistics { // 肈参璸

	private int questionId;
	
	private String questionTitle;
	
	private boolean necessary;
	
	private String option;
	
	private int count;
	
	// 箇砞篶よ猭(⊿Τ把计)
	public Statistics() {
		super();
	}
	
	// 盿┮Τ把计篶よ猭
	public Statistics(int questionId, String questionTitle, boolean necessary, String option, int count) {
		super();
		this.questionId = questionId;
		this.questionTitle = questionTitle;
		this.necessary = necessary;
		this.option = option;
		this.count = count;
	}

	// 穝糤 get
	public int getQuestionId() {
		return questionId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public boolean isNecessary() {
		return necessary;
	}

	public String getOption() {
		return option;
	}

	public int getCount() {
		return count;
	}
	
}
