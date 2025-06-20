package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.dto.ChatRoomCreateDTO;
import com.ddwu.notalonemarket.dto.ChatRoomDTO;
import com.ddwu.notalonemarket.dto.MessageResponseDTO;
import com.ddwu.notalonemarket.service.ChatRoomService;
import com.ddwu.notalonemarket.service.UserService;
import com.ddwu.notalonemarket.util.JwtUtil;
import com.ddwu.notalonemarket.service.ChatMessageService;
import com.ddwu.notalonemarket.service.ChatParticipantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/chatrooms")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatParticipantService participantService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    // 채팅방 생성
    @PostMapping("")
    public Long createRoom(@RequestBody ChatRoomCreateDTO dto) {
        Long roomId = chatRoomService.createRoom(dto);
        // 방장 자동 입장 처리
        participantService.join(roomId, dto.getHostId(), true);
        return roomId;
    }

 // 유저가 참여한 채팅방 목록 조회
    @GetMapping("")
    public ResponseEntity<?> getRooms(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        String token = authHeader.substring(7);
        String loginId = jwtUtil.extractLoginId(token);

        User user = userService.findByLoginId(loginId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        List<ChatRoomDTO> rooms = participantService.getRoomsByUserId(user.getUserId());
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/{roomId}/join")
    public ResponseEntity<?> joinRoom(@PathVariable Long roomId,
                                      @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        String token = authHeader.substring(7);
        String loginId = jwtUtil.extractLoginId(token);

        User user = userService.findByLoginId(loginId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Long userId = user.getUserId();

        if (!participantService.isUserInRoom(userId, roomId)) {
            participantService.join(roomId, userId, false);
        }

        return ResponseEntity.ok(Map.of("message", "joined"));
    }

    //특정 채팅방의 메시지 반환
    @GetMapping("/{roomId}/messages")
    public List<MessageResponseDTO> getMessages(@PathVariable Long roomId) {
        return chatMessageService.getMessageDTOsByRoomId(roomId);
    }

    // 채팅방 거래 완료 처리
    @PutMapping("/{roomId}/complete")
    public String completeRoom(@PathVariable Long roomId) {
        chatRoomService.completeRoom(roomId);
        return "completed";
    }

    
}
