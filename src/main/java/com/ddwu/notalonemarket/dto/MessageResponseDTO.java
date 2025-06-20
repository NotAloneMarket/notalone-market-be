package com.ddwu.notalonemarket.dto;

import java.time.LocalDateTime;

public class MessageResponseDTO {
    private Long senderId; 
    private String message;
    private LocalDateTime timestamp;

    public MessageResponseDTO(Long senderId, String message, LocalDateTime timestamp) {
        this.senderId = senderId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
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
