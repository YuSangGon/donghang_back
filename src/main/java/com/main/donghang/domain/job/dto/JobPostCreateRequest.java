package com.main.donghang.domain.job.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class JobPostCreateRequest {

    private String title;
    private String content;
    private String location;

    private JobType jobType;
    private BigDecimal pay;
    private String position;
    private String countryCode;
    private String countryName;

}
