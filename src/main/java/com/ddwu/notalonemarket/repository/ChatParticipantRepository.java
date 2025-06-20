package com.ddwu.notalonemarket.repository;

import com.ddwu.notalonemarket.domain.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    List<ChatParticipant> findByChatId(Long chatId);
    List<ChatParticipant> findByUserId(Long userId);
}
