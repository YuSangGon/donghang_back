package com.main.donghang.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentCreateRequest {
    private Long parentCommentId;
    private String content;
}
