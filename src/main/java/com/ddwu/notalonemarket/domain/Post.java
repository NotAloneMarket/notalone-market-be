package com.ddwu.notalonemarket.domain;

import com.ddwu.notalonemarket.dto.PostDTO;
import jakarta.persistence.*;


@Entity
@Table(name = "Post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq_gen")
	@SequenceGenerator(name = "post_seq_gen", sequenceName = "SEQ_POST", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;


    private String title;

    @Column(length = 2000)
    private String description;

    private Integer totalAmount;
    private Integer totalQuantity;
    @Column(name = "MY_QUANTITY")
    private Integer myQuantity;

    @Column(name = "PRICE_PER_ITEM")
    private Integer pricePerItem;

    private Integer participantLimit;
    private String productUrl;
    private String imageUrl;
    private String status;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "writer_id")
    private Long writerId;

    public Post() {}

    public Post(Long id, String title, String description, Integer totalAmount, Integer totalQuantity,
                Integer myQuantity, Integer pricePerItem, Integer participantLimit, String productUrl,
                String imageUrl, String status, Long categoryId, Long writerId) {
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
        this.categoryId = categoryId;
        this.writerId = writerId;
    }

    public PostDTO toDTO(String categoryName) {
        return new PostDTO(id, title, description, totalAmount, totalQuantity, myQuantity,
                pricePerItem, participantLimit, productUrl, imageUrl, status,
                categoryName, writerId);
    }

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

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public Long getWriterId() { return writerId; }
    public void setWriterId(Long writerId) { this.writerId = writerId; }
}
