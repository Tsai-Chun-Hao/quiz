package com.example.quiz.service.ifs;

import com.example.quiz.valueobject.BasicResponse;
import com.example.quiz.valueobject.CreateOrUpdateRequest;
import com.example.quiz.valueobject.DeleteRequest;
import com.example.quiz.valueobject.SearchRequest;
import com.example.quiz.valueobject.SearchResponse;

public interface QuizService {

	public BasicResponse createOrUpdate(CreateOrUpdateRequest request);
	
	public SearchResponse search(SearchRequest request);
	
	public BasicResponse delete(DeleteRequest request);
	

}
