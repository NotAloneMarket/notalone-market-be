package com.ddwu.notalonemarket.service;

import org.springframework.stereotype.Service;

import com.ddwu.notalonemarket.dto.ChatMessageDTO;

@Service
public interface ChatMessageService {
    void sendMessage(ChatMessageDTO message);
}
