package com.ssafy.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.dto.LoginParam;
import com.ssafy.project.dto.User;
import com.ssafy.project.dto.UserCheck;
import com.ssafy.project.dto.UserSignIn;
import com.ssafy.project.dto.UserUpdate;
import com.ssafy.project.service.JwtService;
import com.ssafy.project.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	static final int SUCCESS = 1;
	static final int FAIL = -1;
	
	@Autowired
	UserService service;
	
	@Autowired
	private JwtService jwtService;

	// 전체 테이블 조회
	@GetMapping
	public ResponseEntity<List<User>> userList() {
		List<User> result = service.selectUserList();
		if(result != null)
			return new ResponseEntity<List<User>>(result, HttpStatus.OK);
		else
			return new ResponseEntity<List<User>>(result, HttpStatus.NOT_ACCEPTABLE);
	}
	
	// 로그인
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginParam dto, HttpServletResponse response, HttpSession session){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		User user = service.login(dto);
		
		try {
			if(user != null) {	// 로그인 성공했다면 토큰을 생성
				String token = jwtService.create(user);
				logger.trace("로그인 토큰정보 : {}", token);
				
//				토큰 정보는 response의 헤더로 보내고 나머지는 Map에 담는다.
				resultMap.put("accessToken", token);
				resultMap.put("userId", user.getId());
				resultMap.put("userSchool", user.getSchool());
				resultMap.put("userGrade", user.getGrade());
				status = HttpStatus.OK;
			} else {
				resultMap.put("message", "로그인 실패");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		System.out.println(new ResponseEntity<Map<String, Object>>(resultMap, status));
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping(value="/logout")
	public int logout(HttpSession session) {
		
		session.removeAttribute("userDto");
		session.invalidate();
		return SUCCESS;
	}
	
	// 정보 추가(회원 가입)
	@PostMapping
	public ResponseEntity<Integer> signUp(UserSignIn user) {
		if(service.insertUser(user))
			return new ResponseEntity<Integer>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<Integer>(FAIL, HttpStatus.NOT_ACCEPTABLE);
	}
	
	// 정보 조회(마이 페이지)
	@GetMapping(value="/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserCheck> myPage(@PathVariable("userId") Long id) {
		UserCheck result = service.selectUser(id);
		if(result != null)
			return new ResponseEntity<UserCheck>(result, HttpStatus.OK);
		else
			return new ResponseEntity<UserCheck>(result, HttpStatus.NOT_ACCEPTABLE);
	}
	
	// 정보 수정
	@PutMapping(value="/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Integer> editProfile(@PathVariable("userId") Long id, UserUpdate user) {
		if(service.updateUser(id, user))
			return new ResponseEntity<Integer>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<Integer>(FAIL, HttpStatus.NOT_ACCEPTABLE);
	}
	
	// 정보 삭제(회원 탈퇴)
	@DeleteMapping(value="/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Integer> signOut(@PathVariable("userId") Long id) {
		if(service.deleteUser(id))
			return new ResponseEntity<Integer>(SUCCESS, HttpStatus.OK);
		else
			return new ResponseEntity<Integer>(FAIL, HttpStatus.NOT_ACCEPTABLE);
	}
}
