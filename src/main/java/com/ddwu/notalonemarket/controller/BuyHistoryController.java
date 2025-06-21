package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.dto.BuyHistoryResponseDTO;
import com.ddwu.notalonemarket.service.BuyHistoryService;
import com.ddwu.notalonemarket.service.UserService;
import com.ddwu.notalonemarket.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/buyHistory")
public class BuyHistoryController {

    @Autowired
    private BuyHistoryService buyHistoryService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;
    
    @PostMapping("/create")
    public ResponseEntity<String> createBuyHistory(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        String token = authHeader.substring(7);
        String loginId = jwtUtil.extractLoginId(token);

        User user = userService.findByLoginId(loginId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        try {
            buyHistoryService.createBuyHistoryForUser(user.getUserId());
            return ResponseEntity.ok("Buy history created.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<?> getBuyHistory(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("토큰이 필요합니다");
        }

        String token = authHeader.substring(7); // "Bearer " 제거
        String loginId = jwtUtil.extractLoginId(token);
        if (loginId == null) {
            return ResponseEntity.status(401).body("유효하지 않은 토큰입니다");
        }

        Long userId = userService.findUserIdByLoginId(loginId); 
        if (userId == null) {
            return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다");
        }

        List<BuyHistoryResponseDTO> buyHistory = buyHistoryService.getBuyHistory(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("buyHistory", buyHistory);
        return ResponseEntity.ok(response);
    }
}
