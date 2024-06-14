package com.example.quiz.valueobject;

import java.util.List;

import com.example.quiz.entity.Quiz;

public class SearchResponse extends BasicResponse {

	// ��ƬO�h���A�ҥH�O List
	// List<Quiz> �|�O Quiz �O�]���n��㵧��Ƽ��X��
	private List<Quiz> quizList;

	// �w�]���غc��k
	public SearchResponse() {
		super();
	}

//	public SearchResponse(int statusCode, String message) {
//		super(statusCode, message);
//  }

	// 3 �ӰѼƪ��غc��k
	// int statusCode, String message �O�����O���ݩ�
	public SearchResponse(int statusCode, String message, List<Quiz> quizList) {
		super(statusCode, message);
		this.quizList = quizList;
	}

//	public void setQuizList(List<Quiz> quizList) {
//		this.quizList = quizList;
//	}

	// ���� get �M set
	public List<Quiz> getQuizList() {
		return quizList;
	}

	public void setQuizList(List<Quiz> quizList) {
		this.quizList = quizList;
	}
}
