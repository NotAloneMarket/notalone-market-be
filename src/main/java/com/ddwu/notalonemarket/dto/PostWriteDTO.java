package com.ddwu.notalonemarket.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class PostWriteDTO {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String description;

    @NotNull(message = "총 금액은 필수입니다.")
    @Positive(message = "총 금액은 a양수여야 합니다.")
    private Integer totalAmount;

    @NotNull(message = "총 수량은 필수입니다.")
    @Min(value = 2, message = "총 수량은 2개 이상이어야 합니다.")
    private Integer totalQuantity;

    @NotNull(message = "내 수량은 필수입니다.")
    @Min(value = 1, message = "내 수량은 1개 이상이어야 합니다.")
    private Integer myQuantity;

    @NotNull(message = "개당 가격은 필수입니다.")
    @Positive(message = "개당 가격은 양수여야 합니다.")
    private Integer pricePerItem;

    @NotNull(message = "참여 인원 제한은 필수입니다.")
    @Positive(message = "참여 인원 제한은 양수여야 합니다.")
    private Integer participantLimit;

    @NotBlank(message = "상품 URL은 필수입니다.")
    private String productUrl;

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    // 이미지 업로드는 선택
    private MultipartFile image;

    // Getter & Setter
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

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public MultipartFile getImage() { return image; }
    public void setImage(MultipartFile image) { this.image = image; }
}
