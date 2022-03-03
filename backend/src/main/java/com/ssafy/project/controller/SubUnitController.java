package com.ssafy.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.dto.SubUnitList;
import com.ssafy.project.dto.SubUnitResult;
import com.ssafy.project.dto.SubUnitTitle;
import com.ssafy.project.service.SubUnitService;

@RestController
@RequestMapping("subUnit")
public class SubUnitController {

	@Autowired
	SubUnitService service;

	// 전체 테이블 조회
	@GetMapping
	public ResponseEntity<List<SubUnitResult>> getSubUnit() {
		List<SubUnitResult> list = service.findSubUnit();
		if (list == null) {
			return new ResponseEntity<List<SubUnitResult>>(new ArrayList<SubUnitResult>(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<SubUnitResult>>(list, HttpStatus.OK);
		}
	}
	
	// 해당 중단원의 소단원 목록
	@GetMapping(value="/{primaryId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<SubUnitList>> getSubUnitList(@PathVariable("primaryId") Long primaryId) {
		List<SubUnitList> list = service.findSubUnitList(primaryId);
		if (list == null) {
			return new ResponseEntity<List<SubUnitList>>(new ArrayList<SubUnitList>(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<SubUnitList>>(list, HttpStatus.OK);
		}
	}
	
	// 해당 소단원의 제목
	@GetMapping(value="/title/{subId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<SubUnitTitle> getPrimaryUnitTitle(@PathVariable("subId") Long id){
		Optional<SubUnitTitle> unit = service.findSubUnitTitle(id);
		return new ResponseEntity<SubUnitTitle>(unit.get(), HttpStatus.OK);
	}
}
