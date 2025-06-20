package com.ddwu.notalonemarket.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "buy_history")
public class BuyHistory {
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buy_history_seq_gen")
	@SequenceGenerator(name = "buy_history_seq_gen", sequenceName = "SEQ_BUY_HISTORY", allocationSize = 1)
	private Long id;

    
    private Long userId;
    private Integer price;
    private Integer quantity;
    private LocalDateTime completedAt;
    private Long postId;
    private String title;

    public BuyHistory() {}

    public BuyHistory(Long id, Long userId, Integer price, Integer quantity,
            LocalDateTime completedAt, Long postId, String title) {
		this.id = id;
		this.userId = userId;
		this.price = price;
		this.quantity = quantity;
		this.completedAt = completedAt;
		this.postId = postId;
		this.title = title;
	}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
