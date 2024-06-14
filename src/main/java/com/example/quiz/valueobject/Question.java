package com.example.quiz.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {

	// �S���������ƪ�A�ҥH���μg @Column
	private int id;
	
	private String title;

	// options �ﶵ
	private String options;

	private String type;

	@JsonProperty("is_necessary")
	private boolean necessary;

	// �w�]�غc��k(�S���Ѽ�)
	public Question() {
		super();
	}

	// �a�Ҧ��Ѽƪ��غc��k
	public Question(int id, String title, String options, String type, boolean necessary) {
		super();
		this.id = id;
		this.title = title;
		this.options = options;
		this.type = type;
		this.necessary = necessary;
	}

	// �s�W get �M set
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNecessary() {
		return necessary;
	}

	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

}
