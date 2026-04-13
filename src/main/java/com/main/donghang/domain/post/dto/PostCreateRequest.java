package com.main.donghang.domain.post.dto;

import com.main.donghang.domain.post.PostCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostCreateRequest {

    private Long userId;
    private PostCategory category;
    private String title;
    private String content;
    private String location;
    private String countryCode;
    private String countryName;

}
