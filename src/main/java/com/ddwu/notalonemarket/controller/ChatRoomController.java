package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.dto.ChatRoomCreateDTO;
import com.ddwu.notalonemarket.dto.ChatRoomDTO;
import com.ddwu.notalonemarket.dto.MessageResponseDTO;
import com.ddwu.notalonemarket.service.ChatRoomService;
import com.ddwu.notalonemarket.service.ChatMessageService;
import com.ddwu.notalonemarket.service.ChatParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<ChatRoomDTO> getRooms(@RequestParam Long userId) {
        return participantService.getRoomsByUserId(userId);
    }

    // 채팅방에 입장
    @PostMapping("/{roomId}/join")
    public String joinRoom(@PathVariable Long roomId, @RequestParam Long userId) {
        if (!participantService.isUserInRoom(userId, roomId)) {
            participantService.join(roomId, userId, false);
        }
        return "joined";
    }
    
    //특정 채팅방의 메시지 반환
    @GetMapping("/{roomId}/messages")
    public List<MessageResponseDTO> getMessages(@PathVariable Long roomId) {
        return chatMessageService.getMessageDTOsByRoomId(roomId);  // ✅ 인스턴스 호출
    }

    // 채팅방 거래 완료 처리
    @PutMapping("/{roomId}/complete")
    public String completeRoom(@PathVariable Long roomId) {
        chatRoomService.completeRoom(roomId);
        return "completed";
    }

    
}
