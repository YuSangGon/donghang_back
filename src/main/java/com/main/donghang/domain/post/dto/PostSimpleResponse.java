package com.main.donghang.domain.post.dto;

import com.main.donghang.domain.job.dto.JobType;
import com.main.donghang.domain.post.Post;
import com.main.donghang.domain.post.PostCategory;
import com.main.donghang.domain.rent.dto.RentOfferType;
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
    private String countryCode;
    private String countryName;
    private RentOfferType offerType;
    private JobType jobType;

    public PostSimpleResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.location = post.getLocation();
        this.nickname = post.getUser().getNickname();
        this.viewCnt = post.getViewCnt();
        this.category = post.getCategory();
        this.createdAt = post.getCreatedAt();
        this.countryCode = post.getCountryCode();
        this.countryName = post.getCountryName();
        this.offerType = null;
        this.jobType = null;
    }

    public PostSimpleResponse(Post post, RentOfferType offerType) {
        this(post);
        this.offerType = offerType;
    }

    public PostSimpleResponse(Post post, JobType jobType) {
        this(post);
        this.jobType = jobType;
    }

}
