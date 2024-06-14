package com.example.quiz.valueobject;

public class Statistics { // �N��@�D���έp

	private int questionId;
	
	private String questionTitle;
	
	private boolean necessary;
	
	private String option;
	
	private int count;
	
	// �w�]�غc��k(�S���Ѽ�)
	public Statistics() {
		super();
	}
	
	// �a�Ҧ��Ѽƪ��غc��k
	public Statistics(int questionId, String questionTitle, boolean necessary, String option, int count) {
		super();
		this.questionId = questionId;
		this.questionTitle = questionTitle;
		this.necessary = necessary;
		this.option = option;
		this.count = count;
	}

	// �s�W get
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
