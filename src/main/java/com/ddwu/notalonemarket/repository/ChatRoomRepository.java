package com.ddwu.notalonemarket.repository;

import com.ddwu.notalonemarket.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	// postId로 ChatRoom을 찾는 메서드
	Optional<ChatRoom> findByPostId(Long postId);

}