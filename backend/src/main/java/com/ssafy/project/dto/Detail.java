package com.ssafy.project.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="detail")
@Getter
@NoArgsConstructor
public class Detail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long detailId;
	
	@ManyToOne
	@JoinColumn(name = "unit")
	private SubUnit unit;
	
	private String title;				// 세부사항 이름
	private String posture;				// 자세 설명
	private String objective;			// 학습 목표
	private String tip;					// 고득점 팁
	private String guideImage;			// 자세 이미지
	private String iotManual;			// IoT 설정 메뉴얼
	private Long actionNum;
	private Long iotNum;
}
