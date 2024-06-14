package com.example.quiz.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fillin {

	@JsonProperty("question_id")
	private int questionId;

	// 問題
	private String question;

	// 多個選項是用 (;) 串接
	private String options;

	// 多個答案是用 (;) 串接
	private String answer;

	// 單選 多選
	private String type;

	// 必填
	private boolean necessary;

	// 預設建構方法(沒有參數)
	public Fillin() {
		super();
	}

	// 帶所有參數的建構方法
	public Fillin(int questionId, String question, String options, String answer, String type, boolean necessary) {
		super();
		this.questionId = questionId;
		this.question = question;
		this.options = options;
		this.answer = answer;
		this.type = type;
		this.necessary = necessary;
	}

	// 已經有"帶所有參數的建構方法"了，所以不用 set
	public int getQuestionId() {
		return questionId;
	}

	public String getQuestion() {
		return question;
	}

	public String getOptions() {
		return options;
	}

	public String getAnswer() {
		return answer;
	}

	public String getType() {
		return type;
	}

	public boolean isNecessary() {
		return necessary;
	}
}
