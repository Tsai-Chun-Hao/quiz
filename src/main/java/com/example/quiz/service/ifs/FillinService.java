package com.example.quiz.service.ifs;

import com.example.quiz.valueobject.BasicResponse;
import com.example.quiz.valueobject.FeedbackRequest;
import com.example.quiz.valueobject.FeedbackResponse;
import com.example.quiz.valueobject.FillinRequest;
import com.example.quiz.valueobject.StatisticsResponse;

public interface FillinService {
	
public BasicResponse fillin(FillinRequest request);
	
	public FeedbackResponse feedback(FeedbackRequest request);
	
	public StatisticsResponse Statistics(FeedbackRequest request);
	
}
