package com.example.quiz.valueobject;

import java.time.LocalDate;
import java.util.List;

public class FeedbackDetail {

	// 題目
	private String quizName;

	// 描述
	private String description;

	// 開始時間
	private LocalDate startDate;

	// 結束時間
	private LocalDate endDate;

	// 姓名
	private String userName;

	// 電話
	private String phone;

	// 信箱
	private String email;

	// 年齡
	private int age;

	// 一個 Fillin 就代表填一題，List<Fillin> 就代表多題
	private List<Fillin> fillinList;

	// 建構方法
	public FeedbackDetail() {
		super();
	}

	// 帶有參數的建構方法
	public FeedbackDetail(String quizName, String description, LocalDate startDate, LocalDate endDate, String userName,
			String phone, String email, int age, List<Fillin> fillinList) {
		super();
		this.quizName = quizName;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.fillinList = fillinList;
	}

	// 新增 get
	public String getQuizName() {
		return quizName;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public String getUserName() {
		return userName;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public int getAge() {
		return age;
	}

	public List<Fillin> getFillinList() {
		return fillinList;
	}
}
