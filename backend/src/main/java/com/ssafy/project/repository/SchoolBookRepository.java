package com.ssafy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.project.dto.SchoolBook;

@Repository	
public interface SchoolBookRepository extends JpaRepository<SchoolBook, Long> {
	SchoolBook findBySchoolAndGrade(String school, Long grade);
}
