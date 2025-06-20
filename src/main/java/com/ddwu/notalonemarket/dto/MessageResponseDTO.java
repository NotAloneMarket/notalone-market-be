package com.ddwu.notalonemarket.dto;

import java.time.LocalDateTime;

public class MessageResponseDTO {
    private Long senderId; 
    private String senderNickname;
    private String message;
    private LocalDateTime timestamp;

    public MessageResponseDTO(Long senderId, String senderNickname, String message, LocalDateTime timestamp) {
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
    
    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
