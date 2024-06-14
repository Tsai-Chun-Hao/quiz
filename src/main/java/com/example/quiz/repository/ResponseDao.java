package com.example.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Response;

@Repository // 交由 @Repository 託管、依賴(資料處理類...)
// 第一個 T 是 Entity 的名稱，第二個是有設 Id 的屬性
// 在 Dao 中，繼承的 JpaRepository<T,ID>
// 的 ID 原本是有加 @Id 的屬性之資料型態
public interface ResponseDao extends JpaRepository<Response, Integer>{

	public boolean existsByQuizIDAndPhone(int quizId, String phone);
	
	public List<Response> findByQuizID(int quizId);
	
}
