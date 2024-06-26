package com.example.quiz.valueobject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteRequest {

	@JsonProperty("id_list")
	private List<Integer> idList;

	// 預設建構方法
	public DeleteRequest() {
		super();
		System.out.println("預設建構方法");
	}
	// 帶有參數的建構方法
	public DeleteRequest(List<Integer> idList) {
		super();
		this.idList = idList;
		System.out.println("帶有參數的建構方法");
	}
	// request 只需要 get
	public List<Integer> getIdList() {
		return idList;
	}

}