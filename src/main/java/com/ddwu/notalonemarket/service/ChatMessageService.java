package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.domain.ChatMessage;
import com.ddwu.notalonemarket.dto.ChatMessageDTO;
import com.ddwu.notalonemarket.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void saveAndSend(ChatMessageDTO dto) {
        ChatMessage message = new ChatMessage(
                dto.getChatId(),
                dto.getSenderId(),
                dto.getContent(),
                dto.getMessageType(),
                LocalDateTime.now()
        );

        chatMessageRepository.save(message);

        // WebSocket 브로드캐스트
        messagingTemplate.convertAndSend("/topic/chat/" + dto.getChatId(), dto);
    }

    public List<ChatMessage> getMessages(Long chatId) {
        return chatMessageRepository.findByChatIdOrderByCreatedAtAsc(chatId);
    }
}
