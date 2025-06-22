package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.dto.LoginRequest;
import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.service.UserService;
import com.ddwu.notalonemarket.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/thymeleaf-login")
    public String loginPage() {
        System.out.println("/thymeleaf-login 컨트롤러 진입");
        return "login";
    }

    @PostMapping("/user/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userService.findByLoginId(req.getLoginId());
        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }

        String token = jwtUtil.generateToken(user.getLoginId());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userId", user.getUserId());

        return ResponseEntity.ok(result);
    }
}
