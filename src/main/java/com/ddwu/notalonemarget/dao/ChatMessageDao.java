package com.ddwu.notalonemarget.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ddwu.notalonemarget.dto.ChatMessageDTO;

@Repository
public interface ChatMessageDao {
    void save(ChatMessageDTO message);
    List<ChatMessageDTO> findByRoomId(Integer roomId);
}