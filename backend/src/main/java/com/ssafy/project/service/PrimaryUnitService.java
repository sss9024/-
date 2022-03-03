package com.ssafy.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.PrimaryUnit;
import com.ssafy.project.dto.PrimaryUnitList;
import com.ssafy.project.dto.PrimaryUnitResult;
import com.ssafy.project.dto.PrimaryUnitTitle;
import com.ssafy.project.repository.PrimaryUnitRepository;

@Service
public class PrimaryUnitService {

	@Autowired
	PrimaryUnitRepository repo;

	// 전체 테이블 조회
	public List<PrimaryUnitResult> findPrimaryUnit() {
		
		List<PrimaryUnit> units = repo.findAll();
		
		List<PrimaryUnitResult> result = new ArrayList<>();
		units.forEach(unit -> result.add(new PrimaryUnitResult(
				unit.getPriUnitId(),
				unit.getMain().getMainUnitId(),
				unit.getTitle(),
				unit.getImage()
		)));
		
		return result;
	}
	
	// 해당 대단원의 중단원 목록
	public List<PrimaryUnitList> findPrimaryUnitList(Long main) {
		List<PrimaryUnit> units = repo.findByUnitOf(main);
		
		List<PrimaryUnitList> result = new ArrayList<>();
		units.forEach(unit -> result.add(new PrimaryUnitList(
				unit.getPriUnitId(),
				unit.getTitle(),
				unit.getImage()
		)));
		
		return result;
	}
	
	public Optional<PrimaryUnitTitle> findPrimaryUnitTitle(Long id) {
		Optional<PrimaryUnit> unit = repo.findById(id);
		
		PrimaryUnitTitle temp = new PrimaryUnitTitle(unit.get().getPriUnitId(), unit.get().getTitle());
		Optional<PrimaryUnitTitle> result = Optional.of(temp);
		return result;
	}
}
