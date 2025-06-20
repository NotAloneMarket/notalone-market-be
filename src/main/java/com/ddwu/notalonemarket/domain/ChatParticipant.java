package com.ddwu.notalonemarket.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_participant")
public class ChatParticipant {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_participant_seq_gen")
	@SequenceGenerator(name = "chat_participant_seq_gen", sequenceName = "SEQ_CHAT_PARTICIPANT", allocationSize = 1)
	private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "is_hosted", length = 1)
    private String isHosted;

    public ChatParticipant() {}

    public ChatParticipant(Long userId, Long chatId, LocalDateTime joinedAt, String isHosted) {
        this.userId = userId;
        this.chatId = chatId;
        this.joinedAt = joinedAt;
        this.isHosted = isHosted;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getChatId() { return chatId; }
    public void setChatId(Long chatId) { this.chatId = chatId; }
    public LocalDateTime getJoinedAt() { return joinedAt; }
    public void setJoinedAt(LocalDateTime joinedAt) { this.joinedAt = joinedAt; }
    public String getIsHosted() { return isHosted; }
    public void setIsHosted(String isHosted) { this.isHosted = isHosted; }
}
