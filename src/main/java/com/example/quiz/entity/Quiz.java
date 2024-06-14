package com.example.quiz.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quiz")
public class Quiz {

	// �]�� PK �O AI(AI Incremental)�A�ҥH�n�[�W�� @GeneratedValue
	// strategy : �����O AI ���ͦ�����
	// GenerationType.IDENTITY: �N�� PK �Ʀr�Ѹ�Ʈw�Ӧ۰ʼW�[
	// �]���� AI �ҥH�n�[ @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;
//	�ݩʪ���ƫ��A�]�i�H�g�� Integer �A���n�[�W @GeneratedValue(strategy = GenerationType.IDENTITY) �~��
//	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String descriptions;

	// �u������� LocalDate
	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

//	�u���ɶ��� LocalTime
//	private LocalTime time;

//	��� + �ɶ��� LocalDateTime
//	private LocalDateTime fateTime;

	// questions �O�ﶵ
	@Column(name = "questions")
	private String questions;

	@Column(name = "published")
	private boolean published;

	// �w�]�غc��k(�S���Ѽ�)
	public Quiz() {
		super();
	}

	// �a�Ҧ��Ѽƪ��غc��k
	public Quiz(String name, String descriptions, LocalDate startDate, LocalDate endDate, String questions,
			boolean published) {
		super();
		// �i�H����id �]���|�۰ʥͦ�
		this.name = name;
		this.descriptions = descriptions;
		this.startDate = startDate;
		this.endDate = endDate;
		this.questions = questions;
		this.published = published;
	}

	// �a�Ҧ��Ѽƪ��غc��k
	public Quiz(int id, String name, String descriptions, LocalDate startDate, LocalDate endDate, String questions,
			boolean published) {
		super();
		this.id = id;
		this.name = name;
		this.descriptions = descriptions;
		this.startDate = startDate;
		this.endDate = endDate;
		this.questions = questions;
		this.published = published;
	}

	// �s�W get �M set
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	// get ���o���L�ȡA�ҥH�|�b�ݩʦW�٫e�[�W is
	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

}
