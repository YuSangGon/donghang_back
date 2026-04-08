package com.main.donghang.domain.post.dto;

import com.main.donghang.domain.post.Post;
import com.main.donghang.domain.post.PostCategory;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostSimpleResponse {

    private Long id;
    private String title;
    private String location;
    private String nickname;
    private int viewCnt;
    private PostCategory category;
    private LocalDateTime createdAt;

    public PostSimpleResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.location = post.getLocation();
        this.nickname = post.getUser().getNickname();
        this.viewCnt = post.getViewCnt();
        this.category = post.getCategory();
        this.createdAt = post.getCreatedAt();
    }

}
