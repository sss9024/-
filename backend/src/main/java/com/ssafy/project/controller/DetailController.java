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

import com.ssafy.project.dto.DetailList;
import com.ssafy.project.dto.DetailResult;
import com.ssafy.project.dto.DetailTitle;
import com.ssafy.project.service.DetailService;

@RestController
@RequestMapping("detail")
public class DetailController {

	@Autowired
	DetailService service;

	@GetMapping
	public ResponseEntity<List<DetailResult>> getDetail() {
		List<DetailResult> list = service.findDetail();
		if (list == null) {
			return new ResponseEntity<List<DetailResult>>(new ArrayList<DetailResult>(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<DetailResult>>(list, HttpStatus.OK);
		}
	}
	
	// 해당 소단원의 세부내용 목록
	@GetMapping(value = "list/{subId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<DetailList>> getDetailList(@PathVariable("subId") Long id) {
		List<DetailList> list = service.findDetailList(id);
		if (list == null) {
			return new ResponseEntity<List<DetailList>>(new ArrayList<DetailList>(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<DetailList>>(list, HttpStatus.OK);
		}
	}

	// 해당 세부내용의 내용
	@GetMapping(value = "/{detailId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<DetailResult> getDetail(@PathVariable("detailId") Long id) {
		Optional<DetailResult> unit = service.findDetail(id);
		return new ResponseEntity<DetailResult>(unit.get(), HttpStatus.OK);
	}

	// 해당 세부내용의 제목
	@GetMapping(value = "/title/{detailId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<DetailTitle> getDetailTitle(@PathVariable("detailId") Long id) {
		Optional<DetailTitle> unit = service.findDetailTitle(id);
		return new ResponseEntity<DetailTitle>(unit.get(), HttpStatus.OK);
	}
}
