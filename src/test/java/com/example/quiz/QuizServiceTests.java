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

	// ���F�ާ@ QuizService �ҥH�⥦ @Autowired �i��
	@Autowired
	private QuizService quizService;

	// �R��Ƴz�L Dao
	@Autowired
	private QuizDao quizeDao;

	// (���㪺)�����޿�
	@Test
	public void creatTest() {
		// ArrayList ���O�O "�ʺA���O"
		// ���f�O����(List)
		List<Question> questionList = new ArrayList<>();
//		                             �s��  Title            Options�]�ﶵ�^
		questionList.add(new Question(1, "���d�\�H", "���d�\;���ޱ�;�γ�;�N���L", //
				OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(2, "�����~��", "1���\;2���\;3���\;4���\", //
				OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(3, "����", "�ަת���;���A����;�z�����a��(��);��X����", //
				OptionType.SINGLE_CHOICE.getType(), true));

		// �����L�C�A�n�^�h CreateRequest ���� "�w�]" �غc��k�M "�a���Ѽ�" ���غc��k
//		                                                            �ݨ��W��      �y�z              �}�l�ɶ�
		CreateOrUpdateRequest request = new CreateOrUpdateRequest("���\�Yԣ?", "���\�Yԣ?", LocalDate.of(2024, 6, 1), //
//				        �����ɶ�
				LocalDate.of(2024, 6, 1), questionList, true);

		BasicResponse response = quizService.createOrUpdate(request);
		// Assert �O Test �̭�����k
		// ���] response �^�Ǧ^�ӬO 400 �N���O���� 200 �ҥH�n��� create test false!!
		Assert.isTrue(response.getStatusCode() == 200, "create test false!!");
		// �R�����ո�� TODO
	}

	@Test
	public void creatStartDateErrorTest() {
		// ArrayList ���O�O"�ʺA���O"
		// ���f�O����(List)
		List<Question> questionList = new ArrayList<>();
		questionList.add(new Question(1, "���d�\�H", "���d�\;���ޱ�;�γ�;�N���L", //
				OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(2, "����", "1���\;2���\;3���\;4���\", //
				OptionType.SINGLE_CHOICE.getType(), true));
		questionList.add(new Question(3, "����", "�ަת���;���A����;�z�����a��(��);��X����", //
				OptionType.SINGLE_CHOICE.getType(), true));
		// ���� start date error: ���ѬO 2024/5/30�A�ҥH "�}�l" �������O "���" �άO "���e"
		CreateOrUpdateRequest request = new CreateOrUpdateRequest("���\�Yԣ?", "���\�Yԣ?", LocalDate.of(2024, 5, 30), //
				LocalDate.of(2024, 6, 1), questionList, true);
		BasicResponse response = quizService.createOrUpdate(request);
		Assert.isTrue(response.getMessage().equalsIgnoreCase("Param start date error!!"), "create test false!!");
	}

	@Test
	public void creatTest1() {
		// ArrayList ���O�O"�ʺA���O"
		// ���f�O����(List)
		List<Question> questionList = new ArrayList<>();
		questionList.add(new Question(1, "���d�\�H", "���d�\;���ޱ�;�γ�;�N���L", //
				OptionType.SINGLE_CHOICE.getType(), true));

		// ���� name error
		CreateOrUpdateRequest request = new CreateOrUpdateRequest("", "���\�Yԣ?", LocalDate.of(2024, 6, 1), //
				LocalDate.of(2024, 6, 1), questionList, true);
		BasicResponse response = quizService.createOrUpdate(request);
		// Assert �O Test �̭�����k
		// .equalsIgnoreCase �o�Ӥ�k�|�����r�ꪺ�j�p�g�A�ҥH�L�װT�����j�p�g�p��A�u�n���e�ۦP�N�|��^ true
		// ���O "Param name error!!" �̭����Ů�ƶq�n�@�P�AParam name error �o�˴N�|�O����
		Assert.isTrue(response.getMessage().equalsIgnoreCase("Param name error!!"), "create test false!!");

		// ���� start date error: ���]���ѬO 2024/5/30�A�ҥH�}�l�������O��ѩάO���e
		// �C�@�Ӵ��ճ��n request �M response
		request = new CreateOrUpdateRequest("���\�Yԣ?", "���\�Yԣ?", LocalDate.of(2024, 5, 30), //
				LocalDate.of(2024, 6, 1), questionList, true);
		response = quizService.createOrUpdate(request);
		Assert.isTrue(response.getMessage().equalsIgnoreCase("Param start date error!!"), "create test false!!");

		// ���� end date error: ������������}�l�����
		request = new CreateOrUpdateRequest("���\�Yԣ?", "���\�Yԣ?", LocalDate.of(2024, 6, 30), //
				LocalDate.of(2024, 6, 1), questionList, true);
		response = quizService.createOrUpdate(request);
		Assert.isTrue(response.getMessage().equalsIgnoreCase("Param end date error!!"), "create test false!!");

		// �Ѿl���޿�����P�_������A�̫�~�|�O���զ��\������
		request = new CreateOrUpdateRequest("���\�Yԣ?", "���\�Yԣ?", LocalDate.of(2024, 6, 1), //
				LocalDate.of(2024, 6, 1), questionList, true);
		response = quizService.createOrUpdate(request);
		Assert.isTrue(response.getStatusCode() == 200, "create test false!!");
	}

	@Test // �p�� A�BB�BC�BD �X�{������
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
