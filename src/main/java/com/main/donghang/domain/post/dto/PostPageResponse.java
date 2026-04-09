package com.main.donghang.domain.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostPageResponse {

    private List<PostSimpleResponse> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private boolean last;

    public PostPageResponse(
            List<PostSimpleResponse> content,
            int page,
            int size,
            int totalPages,
            long totalElements,
            boolean first,
            boolean last
    ) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.first = first;
        this.last = last;
    }

}
