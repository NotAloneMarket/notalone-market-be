package com.ddwu.notalonemarket.domain;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Post {
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

    // 내부 DB설정
    private static final Map<Long, Post> POST_DB = new HashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    public Post() {}

    public Post(Long id, String title, String description, Long categoryId, Integer totalAmount, Integer myQuantity,
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

    //아직 user없으니까 임시 메소드
    public static Long save(Post post) {
        Long newId = ID_GENERATOR.getAndIncrement();
        post.setId(newId);
        POST_DB.put(newId, post);
        return newId;
    }

    public static List<Post> findAll() {
        return new ArrayList<>(POST_DB.values());
    }

    public static Post findById(Long postId) {
        return POST_DB.get(postId);
    }

    public static void updatePostStatusToComplete(Long postId) {
        Post post = POST_DB.get(postId);
        if (post != null) {
            post.setStatus("COMPLETED");
        }
    }

    public static List<Post> findByUserId(Long userId) {
        // 실제 DB에서는 작성자 ID 기준으로!! 지금은 닉네임 기준
        String nickname = "user" + userId;
        List<Post> result = new ArrayList<>();
        for (Post post : POST_DB.values()) {
            if (nickname.equals(post.getWriterNickname())) {
                result.add(post);
            }
        }
        return result;
    }
}