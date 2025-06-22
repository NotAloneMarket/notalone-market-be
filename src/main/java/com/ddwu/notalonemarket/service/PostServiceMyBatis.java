package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.domain.Category;
import com.ddwu.notalonemarket.domain.Post;
import com.ddwu.notalonemarket.dto.PostDTO;
import com.ddwu.notalonemarket.mapper.PostMapper;
import com.ddwu.notalonemarket.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceMyBatis {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    // 게시글 등록
    public Long createPost(Post post) {
        postMapper.insertPost(post); // useGeneratedKeys로 id 자동 주입됨
        return post.getId();
    }

    // 게시글 상세 조회
    public PostDTO getPostDetail(Long id) {
        Post post = postMapper.selectPostById(id);
        if (post == null) return null;

        String categoryName = categoryRepository.findById(post.getCategoryId())
                .map(Category::getName)
                .orElse("기타");

        return post.toDTO(categoryName);
    }

    // SELLING 상태인 전체 게시글 조회
    public List<PostDTO> getAllSellingPosts() {
        List<Post> postList = postMapper.selectAllSellingPosts();

        return postList.stream()
                .map(post -> {
                    String categoryName = categoryRepository.findById(post.getCategoryId())
                            .map(Category::getName)
                            .orElse("기타");
                    return post.toDTO(categoryName);
                })
                .collect(Collectors.toList());
    }
}
