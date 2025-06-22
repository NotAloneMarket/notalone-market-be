package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.domain.Post;
import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.dto.PostDTO;
import com.ddwu.notalonemarket.dto.PostWriteDTO;
import com.ddwu.notalonemarket.repository.UserRepository;
import com.ddwu.notalonemarket.service.ChatRoomService;
import com.ddwu.notalonemarket.service.ImageUploadService;
import com.ddwu.notalonemarket.service.PostService;
import com.ddwu.notalonemarket.service.PostServiceMyBatis;
import com.ddwu.notalonemarket.service.UserService;
import com.ddwu.notalonemarket.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private PostServiceMyBatis postServiceMyBatis;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChatRoomService chatRoomService;

	@Autowired
	private ImageUploadService imageUploadService;

	@Autowired
	private JwtUtil jwtUtil;

	// ✅ 게시글 작성 (이미지 포함)
	@PostMapping("/write")
	public ResponseEntity<?> createPost(@Valid @ModelAttribute PostWriteDTO dto, BindingResult bindingResult,
			HttpServletRequest request) throws IOException {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
		}

		// ✅ JWT 토큰 추출 및 사용자 조회
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header is missing or invalid");
		}

		String token = authHeader.substring(7);
		String loginId = jwtUtil.extractLoginId(token);
		User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

		// ✅ 이미지 업로드 (Cloudinary)
		String imageUrl = null;
		if (dto.getImage() != null && !dto.getImage().isEmpty()) {
			imageUrl = imageUploadService.uploadImage(dto.getImage());
		}

		// ✅ 게시글 객체 생성 및 저장
		Post post = new Post();
		post.setTitle(dto.getTitle());
		post.setDescription(dto.getDescription());
		post.setTotalAmount(dto.getTotalAmount());
		post.setTotalQuantity(dto.getTotalQuantity());
		post.setMyQuantity(dto.getMyQuantity());
		post.setPricePerItem(dto.getPricePerItem());
		post.setParticipantLimit(dto.getParticipantLimit());
		post.setProductUrl(dto.getProductUrl());
		post.setCategoryId(dto.getCategoryId());
		post.setImageUrl(imageUrl);
		post.setWriterId(user.getUserId());

		Long postId = postServiceMyBatis.createPost(post);
		return ResponseEntity.ok(Map.of("postId", postId));
	}

	@GetMapping("")
	public List<PostDTO> searchAndFilterPosts(@RequestParam(required = false) String keyword,
	                                          @RequestParam(required = false) Long categoryId) {
	    if (keyword != null && categoryId != null) {
	        return postServiceMyBatis.searchByKeywordAndCategoryId(keyword, categoryId);
	    } else if (keyword != null) {
	        return postServiceMyBatis.searchPostsByKeyword(keyword);
	    } else if (categoryId != null) {
	        return postServiceMyBatis.filterPostsByCategoryId(categoryId);
	    } else {
	        return postServiceMyBatis.getAllSellingPosts();
	    }
	}


	// ✅ 단일 게시글 상세 조회
	@GetMapping("/{id}")
	public PostDTO getPostDetail(@PathVariable Long id) {
		return postServiceMyBatis.getPostDetail(id);
	}

	// ✅ 게시글 완료 처리
	@PostMapping("/{id}/complete")
	public String completePost(@PathVariable Long id) {
		postService.completePost(id);
		return "success";
	}

	// ✅ 내가 쓴 게시글 조회
	@GetMapping("/my")
	public ResponseEntity<?> getMyPosts(@RequestHeader("Authorization") String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
		}

		String token = authHeader.substring(7);
		String loginId = jwtUtil.extractLoginId(token);
		User user = userService.findByLoginId(loginId);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		List<PostDTO> myPosts = postServiceMyBatis.getMyPosts(user.getUserId());
		return ResponseEntity.ok(myPosts);
	}

//	// ✅ 키워드 검색
//	@GetMapping(params = "keyword")
//	public List<PostDTO> searchPostsByKeyword(@RequestParam String keyword) {
//		return postServiceMyBatis.searchPostsByKeyword(keyword);
//	}
//
//	// ✅ 카테고리 필터링
//	@GetMapping(params = "category")
//	public List<PostDTO> filterPostsByCategory(@RequestParam String category) {
//		return postServiceMyBatis.filterPostsByCategory(category).stream()
//				.filter(dto -> "selling".equalsIgnoreCase(dto.getStatus())).collect(Collectors.toList());
//	}

	// ✅ 채팅방 ID → 게시글 DTO 조회
	@GetMapping("/from-chatroom/{chatRoomId}")
	public ResponseEntity<?> getPostByChatRoomId(@PathVariable Long chatRoomId) {
		try {
			Long postId = chatRoomService.getPostIdByChatRoomId(chatRoomId);
			PostDTO postDTO = postService.getPostDTOById(postId);
			return ResponseEntity.ok(postDTO);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
		}
	}
}
