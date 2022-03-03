package com.ssafy.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignIn {

	private String loginId;    // 유저 아이디
	private String password;   // 유저 비밀번호
	private String email;      // 유저 이메일
	private String school;     // 학교
	private Long userType;     // 유저 분류(1-학생, 2-교사)
	private Long grade;        // 학년
	private Long height;       // 키
	private Long armLength;    // 팔 길이
	private Long legLength;    // 다리 길이
	
}
