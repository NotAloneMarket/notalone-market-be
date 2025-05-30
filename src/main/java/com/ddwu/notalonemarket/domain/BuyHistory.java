package com.ddwu.notalonemarket.domain;

import java.time.LocalDateTime;

public class BuyHistory {
    private Long id;
    private Long userId;
    private Long postId;
    private Integer quantity;
    private LocalDateTime completedAt;

    public BuyHistory() {}

    public BuyHistory(Long id, Long userId, Long postId, Integer quantity, LocalDateTime completedAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.quantity = quantity;
        this.completedAt = completedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}