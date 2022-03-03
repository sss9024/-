package com.ssafy.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.SchoolBook;
import com.ssafy.project.dto.SchoolBookParam;
import com.ssafy.project.repository.SchoolBookRepository;

@Service
public class SchoolBookService {
	
	@Autowired
	private SchoolBookRepository repo;
	
	// 전체 테이블 조회
	public List<SchoolBook> selectList(){
		return repo.findAll();
	}
	
	// 해당 학교, 학년의 책 찾기
	public SchoolBook selectBook(SchoolBookParam book) {
		return repo.findBySchoolAndGrade(book.getSchool(), book.getGrade());
	}
}
