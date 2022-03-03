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
@Table(name="subunit")
@Getter
@NoArgsConstructor
public class SubUnit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subUnitId;
	
	@ManyToOne
	@JoinColumn(name = "primarys")
	private PrimaryUnit primarys;
	
	private String title;			// 소단원 이름
	private String image;			// 대표 이미지
}
