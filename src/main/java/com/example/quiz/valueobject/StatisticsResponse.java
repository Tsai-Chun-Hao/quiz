package com.example.quiz.valueobject;

import java.time.LocalDate;
import java.util.Map;

public class StatisticsResponse extends BasicResponse {

	private String quizName;

	private LocalDate startDate;

	private LocalDate endDate;

	Map<Integer, Map<String, Integer>> countMap;


	public StatisticsResponse() {
		super();
	}

	public StatisticsResponse(int statusCode, String message) {
		super(statusCode, message);
	}
	
	public StatisticsResponse(int statusCode, String message,String quizName, LocalDate startDate, LocalDate endDate,
			Map<Integer, Map<String, Integer>> countMap) {
		super(statusCode, message);
		this.quizName = quizName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.countMap = countMap;
	}

	public String getQuizName() {
		return quizName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Map<Integer, Map<String, Integer>> getCountMap() {
		return countMap;
	}
}
