package com.example.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Response;

@Repository // ��� @Repository �U�ޡB�̿�(��ƳB�z��...)
// �Ĥ@�� T �O Entity ���W�١A�ĤG�ӬO���] Id ���ݩ�
// �b Dao ���A�~�Ӫ� JpaRepository<T,ID>
// �� ID �쥻�O���[ @Id ���ݩʤ���ƫ��A
public interface ResponseDao extends JpaRepository<Response, Integer>{

	public boolean existsByQuizIDAndPhone(int quizId, String phone);
	
	public List<Response> findByQuizID(int quizId);
	
}
