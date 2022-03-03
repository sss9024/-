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
@Table(name="primaryunit")
@Getter
@NoArgsConstructor
public class PrimaryUnit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long priUnitId;
	
	@ManyToOne
	@JoinColumn(name = "main")
	private MainUnit main;
	
	private String title;			// 중단원 이름
	private String image;			// 대표 이미지
}
