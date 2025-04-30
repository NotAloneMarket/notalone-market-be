package com.ddwu.notalonemarget.service;

import java.util.List;

import com.ddwu.notalonemarget.dto.ChatRoomDTO;

public interface ChatRoomService {
    List<ChatRoomDTO> getChatRoomsByUser(String userId);
    ChatRoomDTO enterChatRoom(Integer roomId, String userId);
    void completeTransaction(Integer roomId, String userId);
}
