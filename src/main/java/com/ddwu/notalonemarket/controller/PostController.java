package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.domain.Post;
import com.ddwu.notalonemarket.dto.PostDTO;
import com.ddwu.notalonemarket.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // 게시글 작성 (이미지 포함)
    @PostMapping("/write")
    public ResponseEntity<?> createPost(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "totalAmount", required = false) Integer totalAmount,
            @RequestParam(value = "totalQuantity", required = false) Integer totalQuantity,
            @RequestParam(value = "myQuantity", required = false) Integer myQuantity,
            @RequestParam(value = "pricePerItem", required = false) Integer pricePerItem,
            @RequestParam(value = "participantLimit", required = false) Integer participantLimit,
            @RequestParam(value = "productUrl", required = false) String productUrl,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "writerId", required = false) Long writerId,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            String imageUrl = null;

            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                String uploadDir = System.getProperty("user.home") + "/notalonemarket/uploads/";

                File dest = new File(uploadDir + fileName);
                dest.getParentFile().mkdirs(); // 디렉토리 없으면 생성
                imageFile.transferTo(dest);

                imageUrl = "/uploads/" + fileName;  // 브라우저 접근용 상대경로
            }

            Post post = new Post();
            post.setTitle(title);
            post.setDescription(description);
            post.setTotalAmount(totalAmount);
            post.setTotalQuantity(totalQuantity);
            post.setMyQuantity(myQuantity);
            post.setPricePerItem(pricePerItem);
            post.setParticipantLimit(participantLimit);
            post.setProductUrl(productUrl);
            post.setImageUrl(imageUrl);
            post.setCategoryId(categoryId);
            post.setWriterId(writerId);
            post.setStatus("진행중");

            Long savedId = postService.createPost(post);
            return ResponseEntity.ok(savedId);

        } catch (IOException e) {
            e.printStackTrace();  // 콘솔 로그 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 업로드 실패: " + e.getMessage());
        }
    }

    // 전체 게시글 조회
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

    // 특정 작성자의 게시글 조회
    @GetMapping("/my")
    public List<PostDTO> getMyPosts(@RequestParam Long writerId) {
        return postService.getMyPosts(writerId);
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
