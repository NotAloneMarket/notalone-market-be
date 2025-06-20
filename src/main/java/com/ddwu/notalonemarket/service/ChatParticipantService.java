package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.domain.ChatParticipant;
import com.ddwu.notalonemarket.domain.ChatRoom;
import com.ddwu.notalonemarket.dto.ChatRoomDTO;
import com.ddwu.notalonemarket.repository.ChatRoomRepository;
import com.ddwu.notalonemarket.repository.ChatParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatParticipantService {

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;
    
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public void join(Long chatId, Long userId, boolean isHost) {
        ChatParticipant participant = new ChatParticipant();
        participant.setChatId(chatId);
        participant.setUserId(userId);
        participant.setIsHosted(isHost ? "Y" : "N");
        participant.setJoinedAt(LocalDateTime.now());

        chatParticipantRepository.save(participant);
    }

    public boolean isUserInRoom(Long userId, Long chatId) {
        return chatParticipantRepository.findByChatId(chatId).stream()
                .anyMatch(p -> p.getUserId().equals(userId));
    }

    public List<ChatRoomDTO> getRoomsByUserId(Long userId) {
        List<Long> chatIds = chatParticipantRepository.findByUserId(userId).stream()
            .map(ChatParticipant::getChatId)
            .distinct()
            .toList();

        List<ChatRoom> rooms = chatRoomRepository.findAllById(chatIds);

        return rooms.stream()
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
    
    public int countParticipantsByRoomId(Long chatRoomId) {
        return chatParticipantRepository.findByChatId(chatRoomId).size();
    }


}
