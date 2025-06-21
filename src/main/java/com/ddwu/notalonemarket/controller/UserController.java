package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.dto.UserRegisterDTO;
import com.ddwu.notalonemarket.service.UserService;
import com.ddwu.notalonemarket.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> register(@RequestBody UserRegisterDTO dto) {
	    User saved = userService.register(dto);
	    return ResponseEntity.created(URI.create("/users/" + saved.getUserId())).build();
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
		String loginId = body.get("loginId");
		String password = body.get("password");

		return userService.login(loginId, password).map(user -> {
			String token = jwtUtil.generateToken(user.getLoginId());
			return ResponseEntity.ok(Map.of("token", token, "userId", user.getUserId()));
		}).orElse(ResponseEntity.status(401).body(Map.of("error", "Invalid credentials")));
	}

	// 프로필 정보 수정 (닉네임/폰번호 + 프로필 이미지)
	@PutMapping("/profile")
	public ResponseEntity<?> updateProfile(
	        @RequestHeader("Authorization") String authHeader,
	        @RequestParam(required = false) String nickname,
	        @RequestParam(required = false) String phoneNum,
	        @RequestParam(required = false) MultipartFile profileImage
	) {
	    try {
	        if (!authHeader.startsWith("Bearer ")) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	        }

	        String token = authHeader.substring(7);
	        String loginId = jwtUtil.extractLoginId(token);
	        User user = userService.findByLoginId(loginId);

	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	        }

	        String imageUrl = null;
	        if (profileImage != null && !profileImage.isEmpty()) {
	            String fileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
	            String uploadDir = System.getProperty("user.home") + "/notalonemarket/uploads/";
	            File dest = new File(uploadDir + fileName);
	            dest.getParentFile().mkdirs();
	            profileImage.transferTo(dest);
	            imageUrl = "/uploads/" + fileName;
	        }

	        userService.updateProfile(user.getUserId(), nickname, phoneNum, imageUrl);

	        return ResponseEntity.ok(Map.of("message", "Profile updated successfully"));

	    } catch (IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of("error", "프로필 수정 실패", "message", e.getMessage()));
	    }
	}




	@GetMapping("/me")
	public ResponseEntity<?> getMyInfo(@RequestHeader("Authorization") String authHeader) {
		if (!authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
		}

		String token = authHeader.substring(7);
		String loginId = jwtUtil.extractLoginId(token); // 토큰에서 loginId 추출

		User user = userService.findByLoginId(loginId);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		return ResponseEntity.ok(Map.of("userId", user.getUserId(), "loginId", user.getLoginId(), "nickname", user.getNickname(), "phoneNum",
				user.getPhoneNum(), "profileImageUrl", user.getProfileImageUrl()));
	}

}
