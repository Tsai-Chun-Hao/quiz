package com.example.quiz.valueobject;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateOrUpdateRequest {

	// �s�W�ݩ� id�A�]���P�_"��s"�B"�s�W"���̾ڭn�ݦ��L id
	private int id;

	// "�ݨ�" �� 4 ���ݩ�
	private String name;

	private String descriptions;

	@JsonProperty("start_date")
	// startDate �m�p�O java �Ϊ��榡
	private LocalDate startDate;

	@JsonProperty("end_date")
	private LocalDate endDate;

	// "���D" �� 4 ���ݩ�
	// questionId�Bcontent�Btype�Bnecessary�A�w�g�b Question �̭��F
	@JsonProperty("question_list")
	private List<Question> questionList;

//	@JsonProperty("question_id")
//	private int questionId;

//	private String content;

//	private String type;

//	@JsonProperty("is_necessary")
//	�p�g�� boolean �w�]�ȷ|�O false (8�Ӱ򥻸�ƫ��A)�A�ҥH�����ˬd"��ƫ��A"�A�j�g�� Boolean �O class�A��ƫ��A�|�O null
//	private boolean necessary;

	// �x�s�A�n���n�o��
	@JsonProperty("is_published")
	// �p�g�� boolean �w�]�ȷ|�O false (8�Ӱ򥻸�ƫ��A)�A�ҥH�����ˬd"��ƫ��A"�A�j�g�� Boolean �O class�A��ƫ��A�|�O
	// null
	private boolean published;

	// �w�]�غc��k
	public CreateOrUpdateRequest() {
		super();
	}

	// �a���Ҧ��Ѽƪ��غc��k
	public CreateOrUpdateRequest(String name, String descriptions, LocalDate startDate, LocalDate endDate,
			List<Question> questionList, boolean published) {
		super();
		this.name = name;
		this.descriptions = descriptions;
		this.startDate = startDate;
		this.endDate = endDate;
		this.questionList = questionList;
		this.published = published;
	}
	// �a���Ҧ��Ѽƪ��غc��k(�]�t id)
	public CreateOrUpdateRequest(int id, String name, String descriptions, LocalDate startDate, LocalDate endDate,
			List<Question> questionList, boolean published) {
		super();
		this.id = id;
		this.name = name;
		this.descriptions = descriptions;
		this.startDate = startDate;
		this.endDate = endDate;
		this.questionList = questionList;
		this.published = published;
	}

	// request ��h�W�Τ��� set
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public boolean isPublished() {
		return published;
	}

}
