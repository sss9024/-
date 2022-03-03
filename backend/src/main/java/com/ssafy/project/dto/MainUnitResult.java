package com.ssafy.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainUnitResult {
	
	private Long mainUnitId;

	private Long sb;
	
	private String title;			// 대단원 이름
	private String intro;			// 소개글
	private String improvement;		// 향상 능력
	private String image;			// 대표 이미지
}
