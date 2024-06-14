package com.example.quiz.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Quiz;

@Repository // ��� @Repository �U�ޡB�̿�(��ƳB�z��...)
// �Ĥ@�� T �O Entity ���W�١A�ĤG�ӬO���] Id ���ݩ�
// �b Dao ���A�~�Ӫ� JpaRepository<T,ID>
// �� ID �쥻�O���[ @Id ���ݩʤ���ƫ��A
public interface QuizDao extends JpaRepository<Quiz, Integer> {

	// findBy �᭱���ݩʦW�١A���O�m�p���g�k: ��ܿz�����(�ݩ�)
	// findByName �� Name �O�ܼƦW��
	// �h���ݩʥ� And �άO Or �s���A�U�O����޿�Ϊk���� && �M ||
	// �Ʀr�i�H����j�p: �j��(GreaterThan): �j�󵥩�(GreaterThanEqual): �p��(LessThan):
	// �p�󵥩�(LessThanEqual)
	// �W���o��k findByNameAndPrice ���� price �O��������
	public List<Quiz> findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String name,
			LocalDate start, LocalDate end);
}
