package com.main.donghang.domain.comment;

import com.main.donghang.domain.comment.dto.CommentCreateRequest;
import com.main.donghang.domain.comment.dto.CommentResponse;
import com.main.donghang.domain.comment.dto.CommentUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/posts/{postId}/comments")
    public Long createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest request
    ) {
        return commentService.create(postId, request);
    }

    @GetMapping("/api/posts/{postId}/comments")
    public List<CommentResponse> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @PutMapping("/api/comments/{commentId}")
    public Long updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request
    ) {
        return commentService.update(commentId, request);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }

}
