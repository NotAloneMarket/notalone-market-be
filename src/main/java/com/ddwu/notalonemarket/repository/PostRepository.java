package com.ddwu.notalonemarket.repository;

import com.ddwu.notalonemarket.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByWriterId(Long writerId);
    List<Post> findByCategoryId(Long categoryId);
    List<Post> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titleKeyword, String descriptionKeyword);
    List<Post> findByStatus(String status);

    // Category 엔티티 name 기준으로 조회
    @Query("SELECT p FROM Post p JOIN Category c ON p.categoryId = c.id WHERE c.name = :categoryName")
    List<Post> findByCategoryName(@Param("categoryName") String categoryName);
}