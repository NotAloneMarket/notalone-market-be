package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.domain.ChatRoom;
import com.ddwu.notalonemarket.dto.ChatRoomCreateDTO;
import com.ddwu.notalonemarket.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public Long createRoom(ChatRoomCreateDTO dto) {
    	
    	 // ✅ 중복 생성 방지 로직 추가
        if (chatRoomRepository.existsByPostId(dto.getPostId())) {
            throw new IllegalArgumentException("이미 해당 게시글에 대한 채팅방이 존재합니다.");
        }
        ChatRoom chatRoom = new ChatRoom(
                dto.getPostId(),
                dto.getHostId(),
                "N", // isCompleted 기본값
                LocalDateTime.now(),
                null
        );
        ChatRoom saved = chatRoomRepository.save(chatRoom);
        return saved.getId();
    }

    public ChatRoom getRoom(Long id) {
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. ID: " + id));
    }
    
    public void completeRoom(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
            .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. ID: " + roomId));

        room.setIsCompleted("Y");
        room.setCompletedAt(LocalDateTime.now());

        chatRoomRepository.save(room);
    }
    
    public Long getPostIdByChatRoomId(Long chatRoomId) {
        ChatRoom room = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. ID: " + chatRoomId));
        return room.getPostId();
    }

}
