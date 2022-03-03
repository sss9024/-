package com.ssafy.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.project.dto.Detail;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {
	@Query(value="select * from detail where unit=:id", nativeQuery=true)
	List<Detail> findByUnitOf(@Param("id") Long id);
}
