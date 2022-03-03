package com.ssafy.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.SubUnit;
import com.ssafy.project.dto.SubUnitList;
import com.ssafy.project.dto.SubUnitResult;
import com.ssafy.project.dto.SubUnitTitle;
import com.ssafy.project.repository.SubUnitRepository;

@Service
public class SubUnitService {

	@Autowired
	SubUnitRepository repo;
	
	// 전체 테이블 조회
	public List<SubUnitResult> findSubUnit(){
		List<SubUnit> unitList = repo.findAll();
		List<SubUnitResult> result = new ArrayList<SubUnitResult>();
		unitList.forEach(e -> result.add(new SubUnitResult(
				e.getSubUnitId(),
				e.getPrimarys().getPriUnitId(),
				e.getTitle(),
				e.getImage())));
		return result;
	}
	
	public List<SubUnitList> findSubUnitList(Long primary){
		List<SubUnit> unitList = repo.findByUnitOf(primary);
		List<SubUnitList> result = new ArrayList<SubUnitList>();
		unitList.forEach(e -> result.add(new SubUnitList(
				e.getSubUnitId(),
				e.getTitle(),
				e.getImage())));
		return result;
	}
	
	public Optional<SubUnitTitle> findSubUnitTitle(Long id) {
		Optional<SubUnit> unit = repo.findById(id);
		
		SubUnitTitle temp = new SubUnitTitle(unit.get().getSubUnitId(), unit.get().getTitle());
		Optional<SubUnitTitle> result = Optional.of(temp);
		return result;
	}
}
