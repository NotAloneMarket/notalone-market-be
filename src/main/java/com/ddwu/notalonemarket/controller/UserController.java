package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.dto.UserRegisterDTO;
import com.ddwu.notalonemarket.service.UserService;
import com.ddwu.notalonemarket.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO dto) {
        User user = new User();
        user.setLoginId(dto.getLoginId()); // 반드시 필요
        user.setPassword(dto.getPassword());
        user.setNickname(dto.getNickname());
        user.setPhoneNum(dto.getPhoneNum());
        user.setAccountNumber(dto.getAccountNumber());

        User saved = userService.register(user);
        return ResponseEntity.created(URI.create("/users/" + saved.getUserId())).build();
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String loginId = body.get("loginId");
        String password = body.get("password");

        return userService.login(loginId, password)
                .map(user -> {
                    String token = jwtUtil.generateToken(user.getLoginId());
                    return ResponseEntity.ok(Map.of(
                            "token", token,
                            "userId", user.getUserId()
                    ));
                })
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Invalid credentials")));
    }


    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestParam Long userId, @RequestBody Map<String, String> body) {
        userService.updateProfile(userId, body.get("newPhone"), body.get("newAccount"));
        return ResponseEntity.ok(Map.of("message", "Profile updated successfully"));
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestParam Long userId, @RequestBody Map<String, String> body) {
        userService.changePassword(userId, body.get("currentPw"), body.get("newPw"));
        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }
}
