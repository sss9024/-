package com.ssafy.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdate {
	private String email;
	private String school;
	private Long grade;
	private Long height;
	private Long armLength;
	private Long legLength;
}
