package com.example.quiz.valueobject;

import java.time.LocalDate;
import java.util.List;

public class FeedbackDetail {

	// �D��
	private String quizName;

	// �y�z
	private String description;

	// �}�l�ɶ�
	private LocalDate startDate;

	// �����ɶ�
	private LocalDate endDate;

	// �m�W
	private String userName;

	// �q��
	private String phone;

	// �H�c
	private String email;

	// �~��
	private int age;

	// �@�� Fillin �N�N���@�D�AList<Fillin> �N�N��h�D
	private List<Fillin> fillinList;

	// �غc��k
	public FeedbackDetail() {
		super();
	}

	// �a���Ѽƪ��غc��k
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

	// �s�W get
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
