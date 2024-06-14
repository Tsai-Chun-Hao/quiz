package com.example.quiz.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Quiz;

@Repository // 交由 @Repository 託管、依賴(資料處理類...)
// 第一個 T 是 Entity 的名稱，第二個是有設 Id 的屬性
// 在 Dao 中，繼承的 JpaRepository<T,ID>
// 的 ID 原本是有加 @Id 的屬性之資料型態
public interface QuizDao extends JpaRepository<Quiz, Integer> {

	// findBy 後面接屬性名稱，都是駝峰式寫法: 表示篩選條件(屬性)
	// findByName 的 Name 是變數名稱
	// 多個屬性用 And 或是 Or 連接，各是表示邏輯用法中的 && 和 ||
	// 數字可以比較大小: 大於(GreaterThan): 大於等於(GreaterThanEqual): 小於(LessThan):
	// 小於等於(LessThanEqual)
	// 上面得方法 findByNameAndPrice 中的 price 是完全等於
	public List<Quiz> findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String name,
			LocalDate start, LocalDate end);
}
