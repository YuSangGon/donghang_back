package com.main.donghang.domain.post;

import com.main.donghang.domain.post.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Long createPost(@RequestBody PostCreateRequest request) {
        return postService.create(request);
    }

    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @PutMapping("/{postId}")
    public Long updatePost(@PathVariable Long postId,
                           @RequestBody PostUpdateRequest request) {
        return postService.update(postId, request);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.delete(postId);
    }

    @GetMapping
    public PostPageResponse getPostsByCategory(
            @RequestParam PostCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return postService.getPostsByCategory(category, page, size);
    }

    @GetMapping("/latest")
    public List<HomePostPreviewResponse> getLatestTop8ByCategory(@RequestParam PostCategory category) {
        return postService.getLatestTop8ByCategory(category);
    }

}
