package com.ssafy.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.project.dto.SubUnit;

@Repository
public interface SubUnitRepository extends JpaRepository<SubUnit, Long> {

	@Query(value="select * from subunit where primarys=:id", nativeQuery=true)
	List<SubUnit> findByUnitOf(@Param("id") Long id);
}
