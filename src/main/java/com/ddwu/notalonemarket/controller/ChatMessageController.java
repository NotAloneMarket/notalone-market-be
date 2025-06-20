package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.dto.ChatMessageDTO;
import com.ddwu.notalonemarket.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173")
@Controller 
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat/send")
    public void sendMessage(ChatMessageDTO messageDTO) {
        chatMessageService.saveAndSend(messageDTO);
    }
}
