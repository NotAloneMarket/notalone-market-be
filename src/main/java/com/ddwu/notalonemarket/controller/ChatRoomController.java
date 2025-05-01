package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.dto.ChatRoomDTO;
import com.ddwu.notalonemarket.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")  // React용 API prefix 추가
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    // 채팅방 목록 조회 (GET /api/chat/rooms?userId=xxx)
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomDTO>> rooms(@RequestParam String userId) {
        List<ChatRoomDTO> rooms = chatRoomService.getChatRoomsByUser(userId);
        return ResponseEntity.ok(rooms);
    }

    // 채팅방 정보 조회 (GET /api/chat/room?roomId=xxx&userId=yyy)
    @GetMapping("/room")
    public ResponseEntity<ChatRoomDTO> room(@RequestParam Integer roomId, @RequestParam String userId) {
        ChatRoomDTO chatRoom = chatRoomService.enterChatRoom(roomId, userId);
        return ResponseEntity.ok(chatRoom);
    }

    // 거래 완료 처리 (POST /api/chat/room/complete)
    @PostMapping("/room/complete")
    public ResponseEntity<String> complete(@RequestParam Integer roomId, @RequestParam String userId) {
        chatRoomService.completeTransaction(roomId, userId);
        return ResponseEntity.ok("success");
    }
}
