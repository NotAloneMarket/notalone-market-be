package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.dto.ChatMessageDTO;
import com.ddwu.notalonemarket.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    // WebSocket 메시지 수신 후 저장 및 브로드캐스트
    @MessageMapping("/chat/send") // 클라이언트에서 /app/chat/send 로 보냄
    public void sendMessage(ChatMessageDTO messageDTO) {
        chatMessageService.saveAndSend(messageDTO);
    }
}
