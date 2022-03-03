package com.ssafy.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCheck {
	private String email;
	private String school;
	private String userType;
	private String grade;
	private String height;
	private String armLength;
	private String legLength;
}
