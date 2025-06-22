package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.dto.UserProfileUpdateDTO;
import com.ddwu.notalonemarket.dto.UserRegisterDTO;
import com.ddwu.notalonemarket.service.UserService;
import com.ddwu.notalonemarket.util.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
	        // 에러 메시지 추출
	        String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
	        return ResponseEntity.badRequest().body(Map.of("error", errorMsg));
	    }
		User saved = userService.register(dto);
	    return ResponseEntity.created(URI.create("/users/" + saved.getUserId())).build();
	}

//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
//		String loginId = body.get("loginId");
//		String password = body.get("password");
//
//		return userService.login(loginId, password).map(user -> {
//			String token = jwtUtil.generateToken(user.getLoginId());
//			return ResponseEntity.ok(Map.of("token", token, "userId", user.getUserId()));
//		}).orElse(ResponseEntity.status(401).body(Map.of("error", "Invalid credentials")));
//	}

	// 프로필 정보 수정 (닉네임/폰번호 + 프로필 이미지)
	@PutMapping("/profile")
	public ResponseEntity<?> updateProfile(
	    @RequestHeader("Authorization") String authHeader,
	    @RequestBody UserProfileUpdateDTO dto
	) {
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	    }

	    String token = authHeader.substring(7);
	    String loginId = jwtUtil.extractLoginId(token);

	    User user = userService.findByLoginId(loginId);
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }

	    userService.updateProfile(user.getUserId(), dto.getNickname(), dto.getPhoneNum(), dto.getProfileImageUrl());

	    return ResponseEntity.ok(Map.of("message", "프로필 수정 완료"));
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> getMyInfo(Authentication authentication) {
	    System.out.println("[GET /user/me] 호출됨");

	    if (authentication == null) {
	        System.out.println("authentication == null (SecurityContext에 인증 정보 없음)");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 정보 없음");
	    }

	    System.out.println("authentication.getPrincipal(): " + authentication.getPrincipal());
	    System.out.println("authentication.getAuthorities(): " + authentication.getAuthorities());
	    System.out.println("authentication.getClass(): " + authentication.getClass().getName());

	    String loginId = authentication.getPrincipal().toString();
	    System.out.println("인증된 사용자 loginId: " + loginId);

	    User user = userService.findByLoginId(loginId);
	    if (user == null) {
	        System.out.println("해당 loginId에 해당하는 사용자 없음");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }

	    System.out.println("사용자 정보 조회 성공");

	    // null-safe Map 생성
	    Map<String, Object> result = new java.util.HashMap<>();
	    result.put("userId", user.getUserId());
	    result.put("loginId", user.getLoginId());
	    result.put("nickname", user.getNickname() != null ? user.getNickname() : "");
	    result.put("phoneNum", user.getPhoneNum() != null ? user.getPhoneNum() : "");
	    result.put("profileImageUrl", user.getProfileImageUrl() != null ? user.getProfileImageUrl() : "");

	    return ResponseEntity.ok(result);
	}
}
