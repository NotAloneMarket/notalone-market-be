package com.ddwu.notalonemarket.dto;

public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private Integer totalAmount;
    private Integer totalQuantity;
    private Integer myQuantity;
    private Integer pricePerItem;
    private Integer participantLimit;
    private String productUrl;
    private String imageUrl;
    private String status;
    private String categoryName; // ← 추가됨
    private Long writerId;

    // 생성자 (Post 엔티티 → DTO 변환 시 사용)
    public PostDTO(Long id, String title, String description, Integer totalAmount, Integer totalQuantity,
                   Integer myQuantity, Integer pricePerItem, Integer participantLimit,
                   String productUrl, String imageUrl, String status, String categoryName, Long writerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.myQuantity = myQuantity;
        this.pricePerItem = pricePerItem;
        this.participantLimit = participantLimit;
        this.productUrl = productUrl;
        this.imageUrl = imageUrl;
        this.status = status;
        this.categoryName = categoryName;
        this.writerId = writerId;
    }

    // 기본 생성자
    public PostDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Integer totalAmount) { this.totalAmount = totalAmount; }

    public Integer getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(Integer totalQuantity) { this.totalQuantity = totalQuantity; }

    public Integer getMyQuantity() { return myQuantity; }
    public void setMyQuantity(Integer myQuantity) { this.myQuantity = myQuantity; }

    public Integer getPricePerItem() { return pricePerItem; }
    public void setPricePerItem(Integer pricePerItem) { this.pricePerItem = pricePerItem; }

    public Integer getParticipantLimit() { return participantLimit; }
    public void setParticipantLimit(Integer participantLimit) { this.participantLimit = participantLimit; }

    public String getProductUrl() { return productUrl; }
    public void setProductUrl(String productUrl) { this.productUrl = productUrl; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Long getWriterId() { return writerId; }
    public void setWriterId(Long writerId) { this.writerId = writerId; }
}
