package com.example.quiz.valueobject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteRequest {

	@JsonProperty("id_list")
	private List<Integer> idList;

	// 箇砞篶よ猭
	public DeleteRequest() {
		super();
		System.out.println("箇砞篶よ猭");
	}
	// 盿Τ把计篶よ猭
	public DeleteRequest(List<Integer> idList) {
		super();
		this.idList = idList;
		System.out.println("盿Τ把计篶よ猭");
	}
	// request 惠璶 get
	public List<Integer> getIdList() {
		return idList;
	}

}