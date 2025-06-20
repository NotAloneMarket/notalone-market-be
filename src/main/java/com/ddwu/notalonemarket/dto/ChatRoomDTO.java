package com.ddwu.notalonemarket.dto;

import java.time.LocalDateTime;

public class ChatRoomDTO {
    private Long id;
    private Long postId;
    private Long hostId;
    private String isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public ChatRoomDTO(Long id, Long postId, Long hostId, String isCompleted,
                       LocalDateTime createdAt, LocalDateTime completedAt) {
        this.id = id;
        this.postId = postId;
        this.hostId = hostId;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
