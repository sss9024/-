package com.ssafy.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.project.dto.MainUnit;

@Repository
public interface MainUnitRepository extends JpaRepository<MainUnit, Long>{
	
	@Query(value="select * from mainunit where book=:id", nativeQuery=true)
	List<MainUnit> findBySB(@Param("id") Long id);
}
