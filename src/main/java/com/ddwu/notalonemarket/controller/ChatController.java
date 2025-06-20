package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.dto.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send") // 클라이언트에서 보낼 주소: /app/chat.send
    public void sendMessage(ChatMessageDTO message) {
        message.setTimestamp(LocalDateTime.now());
        messagingTemplate.convertAndSend("/topic/chat/" + message.getRoomId(), message);
        // 클라이언트는 /topic/chat/{roomId}를 구독해야 함
    }
}
