package com.ddwu.notalonemarket.repository;

import com.ddwu.notalonemarket.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByWriterId(Long writerId);
    List<Post> findByCategoryId(Long categoryId);
}