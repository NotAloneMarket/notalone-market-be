package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.dto.ChatMessageDTO;

public interface ChatMessageService {
    void sendMessage(ChatMessageDTO message);
}
