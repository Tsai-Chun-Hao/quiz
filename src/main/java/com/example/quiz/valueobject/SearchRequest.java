package com.example.quiz.valueobject;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchRequest {
	
	// name �W��
	private String name;
	
	@JsonProperty("start_date")
	private LocalDate startDate;
	
	@JsonProperty("end_date")
	private LocalDate endDate;
	
	// ���ݭn�غc��k�Arequest �u�|�Ψ� get
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
