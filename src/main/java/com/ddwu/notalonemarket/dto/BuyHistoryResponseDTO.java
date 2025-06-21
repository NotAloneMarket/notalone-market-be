package com.ddwu.notalonemarket.dto;

import java.time.LocalDateTime;

public class BuyHistoryResponseDTO {
    private Long postId;
    private String title;
    private Integer price;
    private Integer quantity;
    private LocalDateTime completedAt;
    private String imageUrl;

    public BuyHistoryResponseDTO() {}

    public BuyHistoryResponseDTO(Long postId, String title, Integer price, Integer quantity, LocalDateTime completedAt, String imageUrl) {
        this.postId = postId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.completedAt = completedAt;
        this.imageUrl = imageUrl;
    }

    public Long getPostId() { return postId; }
    public String getTitle() { return title; }
    public Integer getPrice() { return price; }
    public Integer getQuantity() { return quantity; }
    public LocalDateTime getCompletedAt() { return completedAt; }

    public void setPostId(Long postId) { this.postId = postId; }
    public void setTitle(String title) { this.title = title; }
    public void setPrice(Integer price) { this.price = price; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
