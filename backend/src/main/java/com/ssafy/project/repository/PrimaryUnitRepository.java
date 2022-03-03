package com.ssafy.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.project.dto.PrimaryUnit;

@Repository
public interface PrimaryUnitRepository extends JpaRepository<PrimaryUnit, Long> {
	
	@Query(value="select * from primaryunit where main=:id", nativeQuery=true)
	List<PrimaryUnit> findByUnitOf(@Param("id") Long id);
}
