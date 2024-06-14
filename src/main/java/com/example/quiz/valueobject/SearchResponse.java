package com.example.quiz.valueobject;

import java.util.List;

import com.example.quiz.entity.Quiz;

public class SearchResponse extends BasicResponse {

	// 資料是多筆，所以是 List
	// List<Quiz> 會是 Quiz 是因為要把整筆資料撈出來
	private List<Quiz> quizList;

	// 預設的建構方法
	public SearchResponse() {
		super();
	}

//	public SearchResponse(int statusCode, String message) {
//		super(statusCode, message);
//  }

	// 3 個參數的建構方法
	// int statusCode, String message 是父類別的屬性
	public SearchResponse(int statusCode, String message, List<Quiz> quizList) {
		super(statusCode, message);
		this.quizList = quizList;
	}

//	public void setQuizList(List<Quiz> quizList) {
//		this.quizList = quizList;
//	}

	// 產生 get 和 set
	public List<Quiz> getQuizList() {
		return quizList;
	}

	public void setQuizList(List<Quiz> quizList) {
		this.quizList = quizList;
	}
}
