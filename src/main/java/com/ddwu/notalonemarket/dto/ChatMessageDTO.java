package com.ddwu.notalonemarket.dto;

import java.time.LocalDateTime;

public class ChatMessageDTO {
    private Long chatId;
    private Long senderId;
    private String content;
    private String messageType;
    private LocalDateTime createdAt;

    public ChatMessageDTO() {}

    public ChatMessageDTO(Long chatId, Long senderId, String content, String messageType, LocalDateTime createdAt) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.content = content;
        this.messageType = messageType;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Long getChatId() { return chatId; }
    public void setChatId(Long chatId) { this.chatId = chatId; }

    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
