package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.dto.ChatMessageDTO;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Override
    public void sendMessage(ChatMessageDTO message) {
        // 여기서 실제 메시지 처리 로직을 구현하세요.
        System.out.println("메시지 내용: " + message);
    }
}
