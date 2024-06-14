package com.example.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz.service.ifs.FillinService;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.valueobject.BasicResponse;
import com.example.quiz.valueobject.CreateOrUpdateRequest;
import com.example.quiz.valueobject.DeleteRequest;
import com.example.quiz.valueobject.FeedbackRequest;
import com.example.quiz.valueobject.FeedbackResponse;
import com.example.quiz.valueobject.FillinRequest;
import com.example.quiz.valueobject.SearchRequest;
import com.example.quiz.valueobject.SearchResponse;
import com.example.quiz.valueobject.StatisticsResponse;

@CrossOrigin
@RestController
public class QuizController {

	@Autowired
	private FillinService fillinService;
	
	@Autowired
	private QuizService quizService;

	// /quiz �e���� / �i�g�i���g�A/���P�� postmen �̭� 8080/ �᭱�� /
	// @PostMapping: ��ܽШD��k�� Http method �O POST
	// value = "quiz/create": ��ܸӽШD�����|(URL)�A�۩w�q
	@PostMapping(value = "quiz/create_update")
	// @RequestBody: �i�H��~���Ѽƶǰe�L��(�ШD)�� "key��" �������ܼƦW��
	// @RequestBody: �i�H��~���Ф��� JSON ����(key-value) ������۩w�q�� CreateRequest �����ݩʦW�٤�
	// �ç�Ƚᤩ����ܼƸ�
	public BasicResponse create(@RequestBody CreateOrUpdateRequest request) {
		return quizService.createOrUpdate(request);
	}
	
	@PostMapping(value = "quiz/search")
	public SearchResponse search(@RequestBody SearchRequest request) {
		return quizService.search(request);
	}
	
	@PostMapping(value = "quiz/delete")
	public BasicResponse delete(@RequestBody DeleteRequest request) {
		return quizService.delete(request);
	}
	
	@PostMapping(value = "quiz/fillin")
	public BasicResponse fillin(@RequestBody FillinRequest request) {
		return fillinService.fillin(request);
	}
	
	@PostMapping(value = "quiz/feedback")
	public FeedbackResponse feedback(@RequestBody FeedbackRequest request) {
		return fillinService.feedback(request);
	}
	
	@PostMapping(value = "quiz/statistics")
	public StatisticsResponse statistics(@RequestBody FeedbackRequest request) {
		return fillinService.Statistics(request);
	}

}
