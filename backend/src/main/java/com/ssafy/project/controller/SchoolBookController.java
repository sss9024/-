package com.ssafy.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.dto.MainUnitResult;
import com.ssafy.project.dto.MainUnitTitle;
import com.ssafy.project.dto.SchoolBook;
import com.ssafy.project.dto.SchoolBookParam;
import com.ssafy.project.dto.User;
import com.ssafy.project.service.SchoolBookService;

@RestController
@RequestMapping("book")
public class SchoolBookController {

	@Autowired
	SchoolBookService service;

	// 전체 테이블 조회
	@GetMapping
	public ResponseEntity<List<SchoolBook>> bookList() {
		List<SchoolBook> result = service.selectList();
		if (result != null)
			return new ResponseEntity<List<SchoolBook>>(result, HttpStatus.OK);
		else
			return new ResponseEntity<List<SchoolBook>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 학교, 학년으로 찾기
	@PostMapping
	public ResponseEntity<SchoolBook> getBook(@RequestBody SchoolBookParam dto) {
		SchoolBook book = service.selectBook(dto);
		if(book != null) 
			return new ResponseEntity<SchoolBook>(book, HttpStatus.OK);
		else
			return new ResponseEntity<SchoolBook>(new SchoolBook(), HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
}
