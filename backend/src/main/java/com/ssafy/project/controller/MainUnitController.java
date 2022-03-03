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

import com.ssafy.project.dto.MainUnitResult;
import com.ssafy.project.dto.MainUnitTitle;
import com.ssafy.project.service.MainUnitService;

@RestController
@RequestMapping("mainUnit")
public class MainUnitController {

	// private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MainUnitService service;

	// 전체 대단원 목록 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<MainUnitResult>> getMainUnitList() {
		List<MainUnitResult> list = service.findMainUnitList();
		if (list == null) {
			return new ResponseEntity<List<MainUnitResult>>(new ArrayList<MainUnitResult>(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<MainUnitResult>>(list, HttpStatus.OK);
		}
	}
	
	// 해당 교과서(id)의 대단원 목록
	@GetMapping(value="list/{bookId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<MainUnitResult>> getListofBook(@PathVariable("bookId") Long bookId) {
		List<MainUnitResult> list = service.findListOfBook(bookId);
		if (list == null) {
			return new ResponseEntity<List<MainUnitResult>>(new ArrayList<MainUnitResult>(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<MainUnitResult>>(list, HttpStatus.OK);
		}
	}
	
	// 해당 대단원 제목
	@GetMapping(value="title/{mainId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MainUnitTitle> getMainUnitTitle(@PathVariable("mainId") Long id) {
		Optional<MainUnitTitle> unit = service.findMainUnitTitle(id);
		return new ResponseEntity<MainUnitTitle>(unit.get(), HttpStatus.OK);

	}
	
	// 대단원 조회
	@GetMapping(value="/{mainId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MainUnitResult> getMainUnit(@PathVariable("mainId") Long id) {
		Optional<MainUnitResult> unit = service.findMainUnit(id);
		return new ResponseEntity<MainUnitResult>(unit.get(), HttpStatus.OK);

	}
}
