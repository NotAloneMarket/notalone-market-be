package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.domain.Category;
import com.ddwu.notalonemarket.domain.Post;
import com.ddwu.notalonemarket.dto.PostDTO;
import com.ddwu.notalonemarket.mapper.PostMapper;
import com.ddwu.notalonemarket.repository.CategoryRepository;
import com.ddwu.notalonemarket.repository.UserRepository;
import com.ddwu.notalonemarket.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostServiceMyBatis {

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	// 게시글 등록
	public Long createPost(Post post) {
		postMapper.insertPost(post); // useGeneratedKeys로 id 자동 주입됨
		return post.getId();
	}

	// 게시글 상세 조회
	public PostDTO getPostDetail(Long id) {
		Post post = postMapper.selectPostById(id);
		if (post == null)
			return null;

		String categoryName = categoryRepository.findById(post.getCategoryId()).map(Category::getName).orElse("기타");

		PostDTO dto = post.toDTO(categoryName);

		if (post.getWriterId() != null) {
			dto.setNickname(userRepository.findById(post.getWriterId()).map(User::getNickname).orElse(null));
		}

		return dto;
	}

	public List<PostDTO> searchByKeywordAndCategoryId(String keyword, Long categoryId) {
	    Map<String, Object> param = new HashMap<>();
	    param.put("keyword", keyword);
	    param.put("categoryId", categoryId);
	    List<Post> posts = postMapper.searchByKeywordAndCategory(param);
	    return toPostDTOList(posts);
	}

	public List<PostDTO> filterPostsByCategoryId(Long categoryId) {
	    List<Post> posts = postMapper.selectPostsByCategoryId(categoryId);
	    return toPostDTOList(posts);
	}


	// SELLING 상태인 전체 게시글 조회
	public List<PostDTO> getAllSellingPosts() {
		List<Post> postList = postMapper.selectAllSellingPosts();

		return postList.stream().filter(post -> post.getCategoryId() != null && post.getWriterId() != null)
				.map(post -> {
					String categoryName = categoryRepository.findById(post.getCategoryId()).map(Category::getName)
							.orElse("기타");
					PostDTO dto = post.toDTO(categoryName);

					dto.setNickname(userRepository.findById(post.getWriterId()).map(User::getNickname).orElse(null));

					return dto;
				}).collect(Collectors.toList());
	}

	public List<PostDTO> searchPostsByKeyword(String keyword) {
		List<Post> posts = postMapper.selectPostsByKeyword(keyword);
		return toPostDTOList(posts);
	}

	public List<PostDTO> filterPostsByCategory(String category) {
		List<Post> posts = postMapper.selectPostsByCategory(category);
		return toPostDTOList(posts);
	}

	public List<PostDTO> getMyPosts(Long writerId) {
		List<Post> posts = postMapper.selectPostsByWriterId(writerId);
		return toPostDTOList(posts);
	}

	// 공통 로직 추출
	private List<PostDTO> toPostDTOList(List<Post> posts) {
		return posts.stream().map(post -> {
			String categoryName = categoryRepository.findById(post.getCategoryId()).map(Category::getName).orElse("기타");

			PostDTO dto = post.toDTO(categoryName);
			dto.setNickname(userRepository.findById(post.getWriterId()).map(User::getNickname).orElse(null));
			return dto;
		}).collect(Collectors.toList());
	}

}