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
@Table(name="mainunit")
@Getter
@NoArgsConstructor
public class MainUnit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mainUnitId;
	
	@ManyToOne
	@JoinColumn(name = "book")
	private SchoolBook sb;
	
	private String title;			// 대단원 이름
	private String intro;			// 소개글
	private String improvement;		// 향상 능력
	private String image;			// 대표 이미지
}
