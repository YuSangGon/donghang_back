package com.main.donghang.domain.job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class JobPostDetailResponse {

    private Long postId;
    private String title;
    private String content;
    private String nickname;
    private String location;

    private JobType jobType;
    private BigDecimal pay;
    private String position;

    private LocalDateTime createdAt;

}
