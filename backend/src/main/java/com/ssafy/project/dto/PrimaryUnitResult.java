package com.ssafy.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryUnitResult {	

	private Long priUnitId;

	private Long main;
	
	private String title;			// 중단원 이름
	private String image;			// 대표 이미지
}
