package com.ddwu.notalonemarket.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ddwu.notalonemarket.dto.ChatRoomDTO;

@Repository
public interface ChatRoomDao {
    List<ChatRoomDTO> findByUserId(String userId);
    ChatRoomDTO findByRoomId(Integer roomId);
    void updateCompleted(Integer roomId);
}