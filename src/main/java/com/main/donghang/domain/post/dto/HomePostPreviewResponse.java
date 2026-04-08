package com.main.donghang.domain.post.dto;

import com.main.donghang.domain.post.Post;
import lombok.Getter;

@Getter
public class HomePostPreviewResponse {
    private Long id;
    private String location;
    private String title;
    private String nickname;
    private int viewCnt;

    public HomePostPreviewResponse(Post post) {
        this.id = post.getId();
        this.location = post.getLocation();
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.viewCnt = post.getViewCnt();
    }
}
