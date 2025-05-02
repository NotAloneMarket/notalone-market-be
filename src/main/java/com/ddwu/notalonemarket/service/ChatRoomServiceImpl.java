package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.dto.ChatRoomDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    @Override
    public List<ChatRoomDTO> getChatRoomsByUser(String userId) {
        // 임시 구현 (빈 리스트 반환)
        return new ArrayList<>();
    }

    @Override
    public ChatRoomDTO enterChatRoom(Integer roomId, String userId) {
        // 임시 구현 (null 반환)
        return null;
    }

    @Override
    public void completeTransaction(Integer roomId, String userId) {
        // 임시 구현
        System.out.println("거래 완료: roomId=" + roomId + ", userId=" + userId);
    }
}
