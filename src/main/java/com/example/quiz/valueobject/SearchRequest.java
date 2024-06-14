package com.example.quiz.valueobject;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchRequest {
	
	// name 名稱
	private String name;
	
	@JsonProperty("start_date")
	private LocalDate startDate;
	
	@JsonProperty("end_date")
	private LocalDate endDate;
	
	// 不需要建構方法，request 只會用到 get
	public String getName() {
		return name;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	
	
}
