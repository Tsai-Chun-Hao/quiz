package com.example.quiz.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {

	// 沒有對應到資料表，所以不用寫 @Column
	private int id;
	
	private String title;

	// options 選項
	private String options;

	private String type;

	@JsonProperty("is_necessary")
	private boolean necessary;

	// 預設建構方法(沒有參數)
	public Question() {
		super();
	}

	// 帶所有參數的建構方法
	public Question(int id, String title, String options, String type, boolean necessary) {
		super();
		this.id = id;
		this.title = title;
		this.options = options;
		this.type = type;
		this.necessary = necessary;
	}

	// 新增 get 和 set
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
