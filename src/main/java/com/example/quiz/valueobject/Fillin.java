package com.example.quiz.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fillin {

	@JsonProperty("question_id")
	private int questionId;

	// ���D
	private String question;

	// �h�ӿﶵ�O�� (;) �걵
	private String options;

	// �h�ӵ��׬O�� (;) �걵
	private String answer;

	// ��� �h��
	private String type;

	// ����
	private boolean necessary;

	// �w�]�غc��k(�S���Ѽ�)
	public Fillin() {
		super();
	}

	// �a�Ҧ��Ѽƪ��غc��k
	public Fillin(int questionId, String question, String options, String answer, String type, boolean necessary) {
		super();
		this.questionId = questionId;
		this.question = question;
		this.options = options;
		this.answer = answer;
		this.type = type;
		this.necessary = necessary;
	}

	// �w�g��"�a�Ҧ��Ѽƪ��غc��k"�F�A�ҥH���� set
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
