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

	// 因為 PK 是 AI(AI Incremental)，所以要加上此 @GeneratedValue
	// strategy : 指的是 AI 的生成策略
	// GenerationType.IDENTITY: 代表 PK 數字由資料庫來自動增加
	// 因為有 AI 所以要加 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;
//	屬性的資料型態也可以寫成 Integer ，但要加上 @GeneratedValue(strategy = GenerationType.IDENTITY) 才行
//	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String descriptions;

	// 只有日期用 LocalDate
	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

//	只有時間用 LocalTime
//	private LocalTime time;

//	日期 + 時間用 LocalDateTime
//	private LocalDateTime fateTime;

	// questions 是選項
	@Column(name = "questions")
	private String questions;

	@Column(name = "published")
	private boolean published;

	// 預設建構方法(沒有參數)
	public Quiz() {
		super();
	}

	// 帶所有參數的建構方法
	public Quiz(String name, String descriptions, LocalDate startDate, LocalDate endDate, String questions,
			boolean published) {
		super();
		// 可以不用id 因為會自動生成
		this.name = name;
		this.descriptions = descriptions;
		this.startDate = startDate;
		this.endDate = endDate;
		this.questions = questions;
		this.published = published;
	}

	// 帶所有參數的建構方法
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

	// 新增 get 和 set
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

	// get 取得布林值，所以會在屬性名稱前加上 is
	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

}
