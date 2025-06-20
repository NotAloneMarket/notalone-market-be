package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.domain.ChatRoom;
import com.ddwu.notalonemarket.dto.ChatRoomCreateDTO;
import com.ddwu.notalonemarket.dto.ChatRoomDTO;
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
        ChatRoom chatRoom = new ChatRoom(
                dto.getUserId(),
                dto.getPostId(),
                dto.getHostId(),
                "N", // isCompleted 기본값
                LocalDateTime.now(),
                null
        );
        ChatRoom saved = chatRoomRepository.save(chatRoom);
        return saved.getId();
    }

    public List<ChatRoomDTO> getRoomsByUserId(Long userId) {
        return chatRoomRepository.findByUserId(userId).stream()
                .map(room -> new ChatRoomDTO(
                        room.getId(),
                        room.getPostId(),
                        room.getHostId(),
                        room.getIsCompleted(),
                        room.getCreatedAt(),
                        room.getCompletedAt()
                ))
                .collect(Collectors.toList());
    }

    public ChatRoom getRoom(Long id) {
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. ID: " + id));
    }
}
