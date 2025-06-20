package com.ddwu.notalonemarket.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CHATMESSAGE")
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_message_seq_gen")
	@SequenceGenerator(name = "chat_message_seq_gen", sequenceName = "SEQ_CHAT_MESSAGE", allocationSize = 1)
	@Column(name = "message_id")
	private Long id;


    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "sender_id")
    private Long senderId;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "message_type")
    private String messageType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ChatMessage() {}

    public ChatMessage(Long chatId, Long senderId, String content, String messageType, LocalDateTime createdAt) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.content = content;
        this.messageType = messageType;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
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
