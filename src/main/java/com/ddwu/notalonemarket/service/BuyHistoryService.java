package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.domain.*;
import com.ddwu.notalonemarket.dto.BuyHistoryResponseDTO;
import com.ddwu.notalonemarket.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuyHistoryService {

    @Autowired private ChatParticipantRepository chatParticipantRepository;
    @Autowired private ChatRoomRepository chatRoomRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private BuyHistoryRepository buyHistoryRepository;

    public void createBuyHistoryForUser(Long userId) {
        List<ChatParticipant> participantList = chatParticipantRepository.findByUserId(userId);

        for (ChatParticipant participant : participantList) {
            ChatRoom chatRoom = chatRoomRepository.findById(participant.getChatId())
                    .orElseThrow(() -> new RuntimeException("채팅방 없음"));

            if (!"Y".equals(chatRoom.getIsCompleted())) continue;

            Long postId = chatRoom.getPostId();
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("게시글 없음"));

            BuyHistory buyHistory = new BuyHistory();
            buyHistory.setUserId(userId);
            buyHistory.setPostId(post.getId());
            buyHistory.setQuantity(post.getMyQuantity());
            buyHistory.setPrice(post.getPricePerItem());
            buyHistory.setCompletedAt(chatRoom.getCompletedAt());
            buyHistory.setTitle(post.getTitle());

            boolean exists = buyHistoryRepository.existsByUserIdAndPostId(userId, postId);
            if (!exists) {
                buyHistoryRepository.save(buyHistory);
            }
        }
    }

    public List<BuyHistoryResponseDTO> getBuyHistory(Long userId) {
        List<BuyHistory> buyHistories = buyHistoryRepository.findByUserId(userId);
        List<BuyHistoryResponseDTO> result = new ArrayList<>();

        for (BuyHistory history : buyHistories) {
            // 게시글에서 이미지 경로 조회
            String imageUrl = postRepository.findById(history.getPostId())
                    .map(Post::getImageUrl)
                    .orElse(null); // 게시글이 삭제된 경우 null 처리

            result.add(new BuyHistoryResponseDTO(
                history.getPostId(),
                history.getTitle(),
                history.getPrice(),
                history.getQuantity(),
                history.getCompletedAt(),
                imageUrl
            ));
        }

        return result;
    }

}
