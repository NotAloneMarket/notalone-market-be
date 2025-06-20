package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.domain.Category;
import com.ddwu.notalonemarket.domain.Post;
import com.ddwu.notalonemarket.dto.PostDTO;
import com.ddwu.notalonemarket.repository.CategoryRepository;
import com.ddwu.notalonemarket.repository.PostRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Long createPost(Post post) {
        return postRepository.save(post).getId();
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findByStatus("SELLING").stream()
                .map(post -> {
                    String categoryName = getCategoryName(post.getCategoryId());
                    return post.toDTO(categoryName);
                })
                .collect(Collectors.toList());
    }


    public PostDTO getPostDetail(Long id) {
        Optional<Post> postOpt = postRepository.findById(id);
        return postOpt.map(post -> {
            String categoryName = getCategoryName(post.getCategoryId());
            return post.toDTO(categoryName);
        }).orElse(null);
    }

    public void completePost(Long id) {
        postRepository.findById(id).ifPresent(post -> {
            post.setStatus("SOLD");
            postRepository.save(post);
        });
    }

    public List<PostDTO> getMyPosts(Long writerId) {
        return postRepository.findByWriterId(writerId).stream()
                .map(post -> {
                    String categoryName = getCategoryName(post.getCategoryId());
                    return post.toDTO(categoryName);
                })
                .collect(Collectors.toList());
    }

    private String getCategoryName(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(Category::getName)
                .orElse("기타");
    }
    
    public List<PostDTO> searchPostsByKeyword(String keyword) {
        return postRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(post -> {
                    String categoryName = getCategoryName(post.getCategoryId());
                    return post.toDTO(categoryName);
                })
                .collect(Collectors.toList());
    }
    
    public List<PostDTO> filterPostsByCategory(String categoryName) {
        return postRepository
                .findByCategoryName(categoryName)
                .stream()
                .map(post -> {
                    String resolvedCategoryName = getCategoryName(post.getCategoryId());
                    return post.toDTO(resolvedCategoryName);
                })
                .collect(Collectors.toList());
    }
    
    public PostDTO getPostDTOById(Long postId) {
        return getPostDetail(postId); // 이미 구현된 상세 조회 메서드 활용
    }

}
