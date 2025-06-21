package com.ddwu.notalonemarket.repository;

import com.ddwu.notalonemarket.domain.BuyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BuyHistoryRepository extends JpaRepository<BuyHistory, Long> {
    List<BuyHistory> findByPostId(Long postId);
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    List<BuyHistory> findByUserId(Long userId); // 추가
}