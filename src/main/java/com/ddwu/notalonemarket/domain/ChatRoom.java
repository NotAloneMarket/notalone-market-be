package com.ddwu.notalonemarket.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CHATROOM")
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_room_seq_gen")
	@SequenceGenerator(name = "chat_room_seq_gen", sequenceName = "SEQ_CHAT_ROOM", allocationSize = 1)
	private Long id;


    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "host_id")
    private Long hostId;

    @Column(name = "is_completed", length = 1)
    private String isCompleted;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public ChatRoom() {}

    public ChatRoom(Long userId, Long postId, Long hostId, String isCompleted, LocalDateTime createdAt, LocalDateTime completedAt) {
        this.userId = userId;
        this.postId = postId;
        this.hostId = hostId;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    public Long getHostId() { return hostId; }
    public void setHostId(Long hostId) { this.hostId = hostId; }
    public String getIsCompleted() { return isCompleted; }
    public void setIsCompleted(String isCompleted) { this.isCompleted = isCompleted; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
