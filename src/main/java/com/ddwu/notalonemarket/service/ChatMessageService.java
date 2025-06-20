package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.domain.ChatMessage;
import com.ddwu.notalonemarket.dto.ChatMessageDTO;
import com.ddwu.notalonemarket.dto.MessageResponseDTO;
import com.ddwu.notalonemarket.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    @Autowired
    private UserService userService; 

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
    

    public List<MessageResponseDTO> getMessageDTOsByRoomId(Long chatId) {
        List<ChatMessage> messages = chatMessageRepository.findByChatIdOrderByCreatedAtAsc(chatId);
        
        return messages.stream()
            .map(msg -> new MessageResponseDTO(
                msg.getSenderId(),  // 닉네임 대신 userId 직접 전달
                msg.getContent(),
                msg.getCreatedAt()
            ))
            .collect(Collectors.toList());
    }

}
