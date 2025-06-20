package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.domain.ChatParticipant;
import com.ddwu.notalonemarket.repository.ChatParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatParticipantService {

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

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
}
