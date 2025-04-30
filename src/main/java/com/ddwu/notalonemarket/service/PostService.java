package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.domain.Post;
import com.ddwu.notalonemarket.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    public Long createPost(PostDTO postDTO) {
        Post post = toEntity(postDTO);
        return Post.save(post);
    }

    public List<PostDTO> getAllPosts() {
        return Post.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PostDTO getPostDetail(Long postId) {
        return toDTO(Post.findById(postId));
    }

    public String completePost(Long postId) {
        Post.updatePostStatusToComplete(postId);
        return "완료되었습니다";
    }

    public List<PostDTO> getMyPosts(Long userId) {
        return Post.findByUserId(userId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    private PostDTO toDTO(Post post) {
        return new PostDTO(
                post.getId(), post.getTitle(), post.getDescription(), post.getCategoryId(),
                post.getTotalAmount(), post.getMyQuantity(), post.getPricePerItem(),
                post.getParticipantLimit(), post.getProductURL(), post.getImageURL(),
                post.getStatus(), post.getWriterNickname()
        );
    }

    private Post toEntity(PostDTO dto) {
        return new Post(
                null, dto.getTitle(), dto.getDescription(), dto.getCategoryId(),
                dto.getTotalAmount(), dto.getMyQuantity(), dto.getPricePerItem(),
                dto.getParticipantLimit(), dto.getProductURL(), dto.getImageURL(),
                dto.getStatus(), dto.getWriterNickname()
        );
    }
}
