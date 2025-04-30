package com.ddwu.notalonemarget.service;

import com.ddwu.notalonemarget.dto.ChatMessageDTO;

public interface ChatMessageService {
    void sendMessage(ChatMessageDTO message);
}
