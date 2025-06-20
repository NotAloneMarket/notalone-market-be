package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.domain.Post;
import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.dto.PostDTO;
import com.ddwu.notalonemarket.repository.UserRepository;
import com.ddwu.notalonemarket.service.PostService;
import com.ddwu.notalonemarket.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Long createPost(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("totalAmount") Integer totalAmount,
            @RequestParam("totalQuantity") Integer totalQuantity,
            @RequestParam("myQuantity") Integer myQuantity,
            @RequestParam("pricePerItem") Integer pricePerItem,
            @RequestParam("participantLimit") Integer participantLimit,
            @RequestParam("productUrl") String productUrl,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "image", required = false) MultipartFile image,
            HttpServletRequest request
    ) throws IOException {

        // JWT → loginId 추출
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header is missing or invalid");
        }
        String token = authHeader.substring(7);
        String loginId = jwtUtil.extractLoginId(token);

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 이미지 저장 처리
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            String uploadDir = System.getProperty("user.home") + "/notalonemarket/uploads/";
            File dest = new File(uploadDir + fileName);
            dest.getParentFile().mkdirs();
            image.transferTo(dest);
            imageUrl = "/uploads/" + fileName;
        }

        // Post 객체 생성
        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        post.setTotalAmount(totalAmount);
        post.setTotalQuantity(totalQuantity);
        post.setMyQuantity(myQuantity);
        post.setPricePerItem(pricePerItem);
        post.setParticipantLimit(participantLimit);
        post.setProductUrl(productUrl);
        post.setCategoryId(categoryId);
        post.setImageUrl(imageUrl);
        post.setWriterId(user.getUserId());

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
