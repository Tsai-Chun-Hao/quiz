package com.example.quiz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.quiz.constants.OptionType;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.valueobject.BasicResponse;
import com.example.quiz.valueobject.CreateOrUpdateRequest;
import com.example.quiz.valueobject.Question;

//@SpringBootTest
public class QuizServiceTests {

	// 為了操作 QuizService 所以把它 @Autowired 進來
	@Autowired
	private QuizService quizService;

	// 刪資料透過 Dao
	@Autowired
	private QuizDao quizeDao;

	// (完整的)測試邏輯
	@Test
	public void creatTest() {
		// ArrayList 類別是 "動態類別"
		// 接口是介面(List)
		List<Question> questionList = new ArrayList<>();
//		                             編號  Title            Options（選項）
		questionList.add(new Question(1, "健康餐？", "健康餐;炸豬排;煎魚;烤雞腿", //
				OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(2, "丹丹漢堡", "1號餐;2號餐;3號餐;4號餐", //
				OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(3, "炒飯", "豬肉炒飯;海鮮炒飯;干貝馬鈴薯(推);綜合炒飯", //
				OptionType.SINGLE_CHOICE.getType(), true));

		// 有紅蚯蚓，要回去 CreateRequest 產生 "預設" 建構方法和 "帶有參數" 的建構方法
//		                                                            問卷名稱      描述              開始時間
		CreateOrUpdateRequest request = new CreateOrUpdateRequest("午餐吃啥?", "午餐吃啥?", LocalDate.of(2024, 6, 1), //
//				        結束時間
				LocalDate.of(2024, 6, 1), questionList, true);

		BasicResponse response = quizService.createOrUpdate(request);
		// Assert 是 Test 裡面的方法
		// 假設 response 回傳回來是 400 就不是等於 200 所以要顯示 create test false!!
		Assert.isTrue(response.getStatusCode() == 200, "create test false!!");
		// 刪除測試資料 TODO
	}

	@Test
	public void creatStartDateErrorTest() {
		// ArrayList 類別是"動態類別"
		// 接口是介面(List)
		List<Question> questionList = new ArrayList<>();
		questionList.add(new Question(1, "健康餐？", "健康餐;炸豬排;煎魚;烤雞腿", //
				OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(2, "丹丹", "1號餐;2號餐;3號餐;4號餐", //
				OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(3, "丹丹", "豬肉炒飯;海鮮炒飯;干貝馬鈴薯(推);綜合炒飯", //
				OptionType.SINGLE_CHOICE.getType(), true));
		// 測試 start date error: 今天是 2024/5/30，所以 "開始" 日期不能是 "當天" 或是 "之前"
		CreateOrUpdateRequest request = new CreateOrUpdateRequest("午餐吃啥?", "午餐吃啥?", LocalDate.of(2024, 5, 30), //
				LocalDate.of(2024, 6, 1), questionList, true);
		BasicResponse response = quizService.createOrUpdate(request);
		Assert.isTrue(response.getMessage().equalsIgnoreCase("Param start date error!!"), "create test false!!");
	}

	@Test
	public void creatTest1() {
		// ArrayList 類別是"動態類別"
		// 接口是介面(List)
		List<Question> questionList = new ArrayList<>();
		questionList.add(new Question(1, "健康餐？", "健康餐;炸豬排;煎魚;烤雞腿", //
				OptionType.SINGLE_CHOICE.getType(), true));

		// 測試 name error
		CreateOrUpdateRequest request = new CreateOrUpdateRequest("", "午餐吃啥?", LocalDate.of(2024, 6, 1), //
				LocalDate.of(2024, 6, 1), questionList, true);
		BasicResponse response = quizService.createOrUpdate(request);
		// Assert 是 Test 裡面的方法
		// .equalsIgnoreCase 這個方法會忽略字串的大小寫，所以無論訊息的大小寫如何，只要內容相同就會返回 true
		// 但是 "Param name error!!" 裡面的空格數量要一致，Param name error 這樣就會是錯的
		Assert.isTrue(response.getMessage().equalsIgnoreCase("Param name error!!"), "create test false!!");

		// 測試 start date error: 假設今天是 2024/5/30，所以開始日期不能是當天或是之前
		// 每一個測試都要 request 和 response
		request = new CreateOrUpdateRequest("午餐吃啥?", "午餐吃啥?", LocalDate.of(2024, 5, 30), //
				LocalDate.of(2024, 6, 1), questionList, true);
		response = quizService.createOrUpdate(request);
		Assert.isTrue(response.getMessage().equalsIgnoreCase("Param start date error!!"), "create test false!!");

		// 測試 end date error: 結束日期不能比開始日期早
		request = new CreateOrUpdateRequest("午餐吃啥?", "午餐吃啥?", LocalDate.of(2024, 6, 30), //
				LocalDate.of(2024, 6, 1), questionList, true);
		response = quizService.createOrUpdate(request);
		Assert.isTrue(response.getMessage().equalsIgnoreCase("Param end date error!!"), "create test false!!");

		// 剩餘的邏輯全部判斷完之後，最後才會是測試成功的情境
		request = new CreateOrUpdateRequest("午餐吃啥?", "午餐吃啥?", LocalDate.of(2024, 6, 1), //
				LocalDate.of(2024, 6, 1), questionList, true);
		response = quizService.createOrUpdate(request);
		Assert.isTrue(response.getStatusCode() == 200, "create test false!!");
	}

	@Test // 計算 A、B、C、D 出現的次數
	public void test() {
		List<String> list = List.of("A", "B", "C", "D", "E");
		String str = "AABBBCDDAEEEAACCDD";
		Map<String, Integer> map = new HashMap<>();
		for (String item : list) {
			String newString = str.replace(item, "");
			int count = str.length() - newString.length();
			map.put(item, count);
		}
		System.out.println(map);
		System.out.println("==============================");
	}
}
