package com.main.donghang.domain.post.dto;

import com.main.donghang.domain.post.PostCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostUpdateRequest {

    private String title;
    private String content;
    private String location;
    private PostCategory category;
    private String countryCode;
    private String countryName;
}
