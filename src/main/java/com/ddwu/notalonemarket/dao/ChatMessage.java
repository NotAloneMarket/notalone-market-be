package com.ddwu.notalonemarket.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ddwu.notalonemarket.dto.ChatMessageDTO;

@Repository
public interface ChatMessageDao {
    void save(ChatMessageDTO message);
    List<ChatMessageDTO> findByRoomId(Integer roomId);
}