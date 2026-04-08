package com.main.donghang.domain.post.dto;

import com.main.donghang.domain.post.Post;
import com.main.donghang.domain.post.PostCategory;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private Long id;
    private Long userId;
    private String nickname;
    private PostCategory category;
    private String title;
    private String content;
    private String location;
    private int viewCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.nickname = post.getUser().getNickname();
        this.category = post.getCategory();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.location = post.getLocation();
        this.viewCnt = post.getViewCnt();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
