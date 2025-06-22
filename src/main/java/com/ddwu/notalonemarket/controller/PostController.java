package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.domain.Post;
import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.dto.PostDTO;
import com.ddwu.notalonemarket.dto.PostWriteDTO;
import com.ddwu.notalonemarket.mapper.PostMapper;

import com.ddwu.notalonemarket.repository.UserRepository;
import com.ddwu.notalonemarket.service.ChatRoomService;
import com.ddwu.notalonemarket.service.PostService;
import com.ddwu.notalonemarket.service.PostServiceMyBatis;
import com.ddwu.notalonemarket.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import com.ddwu.notalonemarket.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.*;

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

    @Autowired
    private ChatRoomService chatRoomService;
    
    @Autowired
    private PostServiceMyBatis postServiceMyBatis;


 // 게시글 작성 (이미지 포함)
    @PostMapping("/write")
    public ResponseEntity<?> createPost( @Valid @ModelAttribute PostWriteDTO dto, BindingResult bindingResult,HttpServletRequest request
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header is missing or invalid");
        }
        String token = authHeader.substring(7);
        String loginId = jwtUtil.extractLoginId(token);
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 이미지 저장
        String imageUrl = null;
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + dto.getImage().getOriginalFilename();
            String uploadDir = System.getProperty("user.home") + "/notalonemarket/uploads/";
            File dest = new File(uploadDir + fileName);
            dest.getParentFile().mkdirs();
            dto.getImage().transferTo(dest);
            imageUrl = "/uploads/" + fileName;
        }

        // Post 객체 생성
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
    public List<PostDTO> getAllPosts() {
        return postServiceMyBatis.getAllSellingPosts();
    }


    // 단일 게시글 상세 조회
    @GetMapping("/{id}")
    public PostDTO getPostDetail(@PathVariable Long id) {
        return postServiceMyBatis.getPostDetail(id);
    }


    // 게시글 상태 완료 처리
    @PostMapping("/{id}/complete")
    public String completePost(@PathVariable Long id) {
        postService.completePost(id);
        return "success";
    }

 // 키워드 검색
    @GetMapping(params = "keyword")
    public List<PostDTO> searchPostsByKeyword(@RequestParam String keyword) {
        return postServiceMyBatis.searchPostsByKeyword(keyword);
    }

    // 카테고리 필터링
    @GetMapping(params = "category")
    public List<PostDTO> filterPostsByCategory(@RequestParam String category) {
        return postServiceMyBatis.filterPostsByCategory(category);
    }

    // 내가 쓴 게시글 조회
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

    
    //채팅방 id로 postDTO 찾기
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
