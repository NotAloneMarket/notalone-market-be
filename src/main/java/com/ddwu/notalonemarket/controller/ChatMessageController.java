package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.dto.ChatMessageDTO;
import com.ddwu.notalonemarket.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ws/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> send(@RequestBody ChatMessageDTO message) {
        chatMessageService.sendMessage(message);
        return ResponseEntity.ok().build();
    }
}
