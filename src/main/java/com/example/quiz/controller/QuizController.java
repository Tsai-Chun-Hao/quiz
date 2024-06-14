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

	// /quiz 前面的 / 可寫可不寫，/等同於 postmen 裡面 8080/ 後面的 /
	// @PostMapping: 表示請求方法的 Http method 是 POST
	// value = "quiz/create": 表示該請求的路徑(URL)，自定義
	@PostMapping(value = "quiz/create_update")
	// @RequestBody: 可以把外面參數傳送過來(請求)的 "key值" 對應到變數名稱
	// @RequestBody: 可以把外部請中的 JSON 物件(key-value) 對應到自定義的 CreateRequest 中的屬性名稱中
	// 並把值賦予到該變數裡
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
