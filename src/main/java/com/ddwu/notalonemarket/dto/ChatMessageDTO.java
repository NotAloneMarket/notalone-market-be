package com.ddwu.notalonemarket.dto;

import java.time.LocalDateTime;

public class ChatMessageDTO {
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime timestamp;

    // 기본 생성자
    public ChatMessageDTO() {}

    // 생성자
    public ChatMessageDTO(String roomId, String sender, String message, LocalDateTime timestamp) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters / Setters
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
