package com.main.donghang.domain.comment.dto;

import com.main.donghang.domain.comment.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private Long id;
    private Long postId;
    private Long userId;
    private String nickname;
    private Long parentCommentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.userId = comment.getUser().getId();
        this.nickname = comment.getUser().getNickname();
        this.parentCommentId = comment.getParentComment() != null
                ? comment.getParentComment().getId()
                : null;
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
