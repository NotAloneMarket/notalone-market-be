package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.domain.Post;
import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.dto.PostDTO;
import com.ddwu.notalonemarket.repository.UserRepository;
import com.ddwu.notalonemarket.service.PostService;
import com.ddwu.notalonemarket.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ddwu.notalonemarket.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	private UserService userService;


    // 게시글 작성 (이미지 포함)
    @PostMapping("/write")
    public Long createPost(@RequestBody Post post, HttpServletRequest request) {
        // 1. JWT 토큰에서 loginId 추출
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header is missing or invalid");
        }
        
        String token = authHeader.substring(7); // "Bearer " 제거
        String loginId = jwtUtil.extractLoginId(token);

        // 2. loginId로 사용자 조회
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 3. writerId 설정
        post.setWriterId(user.getUserId());

        // 4. 저장
        return postService.createPost(post);
    }


    @GetMapping("")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    // 단일 게시글 상세 조회
    @GetMapping("/{id}")
    public PostDTO getPostDetail(@PathVariable Long id) {
        return postService.getPostDetail(id);
    }

    // 게시글 상태 완료 처리
    @PostMapping("/{id}/complete")
    public String completePost(@PathVariable Long id) {
        postService.completePost(id);
        return "success";
    }

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

        List<PostDTO> myPosts = postService.getMyPosts(user.getUserId());
        return ResponseEntity.ok(myPosts);
    }


    // 키워드로 검색
    @GetMapping(params = "keyword")
    public List<PostDTO> searchPostsByKeyword(@RequestParam String keyword) {
        return postService.searchPostsByKeyword(keyword);
    }

    // 카테고리로 필터링
    @GetMapping(params = "category")
    public List<PostDTO> filterPostsByCategory(@RequestParam String category) {
        return postService.filterPostsByCategory(category);
    }
}
