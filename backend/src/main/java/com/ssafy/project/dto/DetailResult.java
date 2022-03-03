package com.ssafy.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailResult {	

	private Long detailId;
	
	private Long unit;
	
	private String title;				// 세부사항 이름
	private String posture;				// 자세 설명
	private String objective;			// 학습 목표
	private String tip;					// 고득점 팁
	private String guideImage;			// 자세 이미지
	private String iotManual;			// IoT 설정 메뉴얼
	private Long actionNum;
	private Long iotNum;

}	
