package com.ddwu.notalonemarket.dto;

public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private Long categoryId;
    private Integer totalAmount;
    private Integer myQuantity;
    private Integer pricePerItem;
    private Integer participantLimit;
    private String productURL;
    private String imageURL;
    private String status;
    private String writerNickname;

    public PostDTO() {}

    public PostDTO(Long id, String title, String description, Long categoryId, Integer totalAmount, Integer myQuantity,
                   Integer pricePerItem, Integer participantLimit, String productURL, String imageURL, String status,
                   String writerNickname) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.totalAmount = totalAmount;
        this.myQuantity = myQuantity;
        this.pricePerItem = pricePerItem;
        this.participantLimit = participantLimit;
        this.productURL = productURL;
        this.imageURL = imageURL;
        this.status = status;
        this.writerNickname = writerNickname;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public Integer getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Integer totalAmount) { this.totalAmount = totalAmount; }

    public Integer getMyQuantity() { return myQuantity; }
    public void setMyQuantity(Integer myQuantity) { this.myQuantity = myQuantity; }

    public Integer getPricePerItem() { return pricePerItem; }
    public void setPricePerItem(Integer pricePerItem) { this.pricePerItem = pricePerItem; }

    public Integer getParticipantLimit() { return participantLimit; }
    public void setParticipantLimit(Integer participantLimit) { this.participantLimit = participantLimit; }

    public String getProductURL() { return productURL; }
    public void setProductURL(String productURL) { this.productURL = productURL; }

    public String getImageURL() { return imageURL; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getWriterNickname() { return writerNickname; }
    public void setWriterNickname(String writerNickname) { this.writerNickname = writerNickname; }
}
