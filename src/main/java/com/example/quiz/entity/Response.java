package com.example.quiz.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "response")
public class Response {

	// �]�� PK �O AI(AI Incremental)�A�ҥH�n�[�W�� @GeneratedValue
	// strategy : �����O AI ���ͦ�����
	// GenerationType.IDENTITY: �N�� PK �Ʀr�Ѹ�Ʈw�Ӧ۰ʼW�[
	// �]���� AI �ҥH�n�[ @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "quiz_id")
	private int quizID;

	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "age")
	private int age;

	@Column(name = "fillin")
	private String fillin;
	
	//���������w�]�ȡA���ݭn�غc��k
	@Column(name = "fillin_date_time")
	private LocalDateTime fillinDateTime = LocalDateTime.now();

	// �w�]�غc��k(�S���Ѽ�)
	public Response() {
		super();
	}

	// �a�Ҧ��Ѽƪ��غc��k
	public Response(int quizID, String name, String phone, String email, int age, String fillin) {
		super();
		this.quizID = quizID;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.fillin = fillin;
	}
	
	// �s�W get �M set
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuizID() {
		return quizID;
	}

	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFillin() {
		return fillin;
	}

	public void setFillin(String fillin) {
		this.fillin = fillin;
	}

	public LocalDateTime getFillinDateTime() {
		return fillinDateTime;
	}

	public void setFillinDateTime(LocalDateTime fillinDateTime) {
		this.fillinDateTime = fillinDateTime;
	}
}
