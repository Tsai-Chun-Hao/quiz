package com.example.quiz.valueobject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteRequest {

	@JsonProperty("id_list")
	private List<Integer> idList;

	// �w�]�غc��k
	public DeleteRequest() {
		super();
		System.out.println("�w�]�غc��k");
	}
	// �a���Ѽƪ��غc��k
	public DeleteRequest(List<Integer> idList) {
		super();
		this.idList = idList;
		System.out.println("�a���Ѽƪ��غc��k");
	}
	// request �u�ݭn get
	public List<Integer> getIdList() {
		return idList;
	}

}