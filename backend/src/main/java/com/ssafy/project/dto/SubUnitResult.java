package com.ssafy.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubUnitResult {

	private Long subUnitId;

	private Long primarys;
	
	private String title;
	private String image;
}
