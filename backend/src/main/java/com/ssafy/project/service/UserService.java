package com.ssafy.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.project.dto.LoginParam;
import com.ssafy.project.dto.User;
import com.ssafy.project.dto.UserCheck;
import com.ssafy.project.dto.UserSignIn;
import com.ssafy.project.dto.UserUpdate;
import com.ssafy.project.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	// 전체 테이블 조회
	public List<User> selectUserList() {
		return repo.findAll();
	}
	
	// 로그인
	public User login(LoginParam dto) {
		User user = repo.findByLogin(dto.getUserId());

		if (user != null && user.getPassword().equals(dto.getPassword())) {
			return user;
		} else {
			return null;
		}
	}

	// 회원 저장
	public boolean insertUser(UserSignIn user) {
		try {
			User input = new User(user.getLoginId(), user.getPassword(), user.getEmail(), user.getSchool(),
					user.getUserType(), user.getGrade(), user.getHeight(), user.getArmLength(), user.getLegLength());
			repo.save(input);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	// 회원 조회
	public UserCheck selectUser(Long id) {
		Optional<User> user = repo.findById(id);
		if (user.isPresent()) {
			UserCheck result = new UserCheck();
			result.setEmail(user.get().getEmail());
			result.setSchool(user.get().getSchool());
			if (user.get().getUserType() == 1)
				result.setUserType("학생");
			else
				result.setUserType("교사");
			result.setGrade(user.get().getGrade() + "학년");
			result.setHeight(user.get().getHeight() + "cm");
			result.setArmLength(user.get().getArmLength() + "cm");
			result.setLegLength(user.get().getLegLength() + "cm");

			return result;
		} else {
			return null;
		}
	}

	// 회원 수정
	public boolean updateUser(Long id, UserUpdate user) {
		Optional<User> e = repo.findById(id);
		if (e.isPresent()) {
			e.get().setEmail(user.getEmail());
			e.get().setSchool(user.getSchool());
			e.get().setGrade(user.getGrade());
			e.get().setHeight(user.getHeight());
			e.get().setArmLength(user.getArmLength());
			e.get().setLegLength(user.getLegLength());
			repo.save(e.get());
			return true;
		} else {
			System.out.println("Can't Find Object");
			return false;
		}
	}

	// 회원 삭제
	public boolean deleteUser(Long id) {
		try {
			repo.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
