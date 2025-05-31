package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.domain.Post;
import com.ddwu.notalonemarket.dto.PostDTO;
import com.ddwu.notalonemarket.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/write")
    public Long createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @GetMapping("")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostDTO getPostDetail(@PathVariable Long id) {
        return postService.getPostDetail(id);
    }

    @PostMapping("/{id}/complete")
    public String completePost(@PathVariable Long id) {
        postService.completePost(id);
        return "success";
    }

    @GetMapping("/my")
    public List<PostDTO> getMyPosts(@RequestParam Long writerId) {
        return postService.getMyPosts(writerId);
    }
}
