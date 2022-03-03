package com.ssafy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.project.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value="select * from User where loginId=:id", nativeQuery=true)
	User findByLogin(@Param("id") String id);
}
