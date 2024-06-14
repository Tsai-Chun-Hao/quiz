package com.example.quiz.valueobject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FillinRequest {

	@JsonProperty("quiz_id")
	private int quizId;

	// ﹎
	private String name;

	// 筿杠
	private String phone;

	// 獺絚
	private String email;

	// 闹
	private int age;

	@JsonProperty("fillin_list")
	//  Fillin 碞恶肈List<Fillin> 碞肈
	private List<Fillin> fillinList;

//	private List<Fillin> fillinList;

	// 箇砞篶よ猭(⊿Τ把计)倒 Springboot ノ
	public FillinRequest() {
		super();
	}

	// 虫じ代刚穦ノ┮穝糤 "盿┮Τ把计篶よ猭"
	// 盿Τ把计篶よ猭( Fillin)
	public FillinRequest(int quizId, String name, String phone, String email, int age, List<Fillin> fillinList) {
		super();
		this.quizId = quizId;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.fillinList = fillinList;
	}

	// 穝糤 get
	public int getQuizId() {
		return quizId;
	}

	public String getName() {
		return name;
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
