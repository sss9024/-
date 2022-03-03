package com.ssafy.project.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String loginId;    // 유저 아이디
	@NonNull
	private String password;   // 유저 비밀번호
	@NonNull
	private String email;      // 유저 이메일
	@NonNull
	private String school;     // 학교
	@NonNull
	private Long userType;     // 유저 분류(1-학생, 2-교사)
	@NonNull
	private Long grade;        // 학년
	@NonNull
	private Long height;       // 키
	@NonNull
	private Long armLength;    // 팔 길이
	@NonNull
	private Long legLength;    // 다리 길이

}
