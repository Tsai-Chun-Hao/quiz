package com.example.quiz.valueobject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FillinRequest {

	@JsonProperty("quiz_id")
	private int quizId;

	// �m�W
	private String name;

	// �q��
	private String phone;

	// �H�c
	private String email;

	// �~��
	private int age;

	@JsonProperty("fillin_list")
	// �@�� Fillin �N�N���@�D�AList<Fillin> �N�N��h�D
	private List<Fillin> fillinList;

//	private List<Fillin> fillinList;

	// �w�]�غc��k(�S���Ѽ�)�A�� Springboot �Ϊ�
	public FillinRequest() {
		super();
	}

	// �]���椸���շ|�Ψ�A�ҥH�s�W "�a�Ҧ��Ѽƪ��غc��k"
	// �a���Ѽƪ��غc��k(�t Fillin)
	public FillinRequest(int quizId, String name, String phone, String email, int age, List<Fillin> fillinList) {
		super();
		this.quizId = quizId;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.fillinList = fillinList;
	}

	// �s�W get
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
