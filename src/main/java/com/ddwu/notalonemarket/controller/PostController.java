package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.dto.PostDTO;
import com.ddwu.notalonemarket.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody PostDTO postDTO) {
        Long postId = postService.createPost(postDTO);
        return ResponseEntity.ok(postId);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostDetail(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostDetail(id));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<String> completePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.completePost(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<PostDTO>> getMyPosts(HttpSession session) {
        Long userId = (Long) session.getAttribute("loginUserId");
        return ResponseEntity.ok(postService.getMyPosts(userId));
    }
}
