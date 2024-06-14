package com.example.quiz.valueobject;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateOrUpdateRequest {

	// 新增屬性 id，因為判斷"更新"、"新增"的依據要看有無 id
	private int id;

	// "問卷" 的 4 個屬性
	private String name;

	private String descriptions;

	@JsonProperty("start_date")
	// startDate 駝峰是 java 用的格式
	private LocalDate startDate;

	@JsonProperty("end_date")
	private LocalDate endDate;

	// "問題" 的 4 個屬性
	// questionId、content、type、necessary，已經在 Question 裡面了
	@JsonProperty("question_list")
	private List<Question> questionList;

//	@JsonProperty("question_id")
//	private int questionId;

//	private String content;

//	private String type;

//	@JsonProperty("is_necessary")
//	小寫的 boolean 預設值會是 false (8個基本資料型態)，所以不用檢查"資料型態"，大寫的 Boolean 是 class，資料型態會是 null
//	private boolean necessary;

	// 儲存，要不要發布
	@JsonProperty("is_published")
	// 小寫的 boolean 預設值會是 false (8個基本資料型態)，所以不用檢查"資料型態"，大寫的 Boolean 是 class，資料型態會是
	// null
	private boolean published;

	// 預設建構方法
	public CreateOrUpdateRequest() {
		super();
	}

	// 帶有所有參數的建構方法
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
	// 帶有所有參數的建構方法(包含 id)
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

	// request 原則上用不到 set
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
