package com.example.quiz.valueobject;

public class FeedbackRequest {

	private int quizId;

	// �w�]�غc��k(�S���Ѽ�)�A�� Springboot �Ϊ�
	public FeedbackRequest() {
		super();
	}

	// �]���椸���շ|�Ψ�A�ҥH�s�W "�a�Ҧ��Ѽƪ��غc��k"
	// �u�n���a���Ѽƪ��غc�A�N�n���w�]�غc��k
	public FeedbackRequest(int quizId) {
		super();
		this.quizId = quizId;
	}

	// �s�W get
	public int getQuizId() {
		return quizId;
	}

}
