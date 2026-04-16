package com.main.donghang.domain.market.dto;

import com.main.donghang.domain.market.MarketType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MarketPostDetailResponse {

    private Long postId;
    private Long userId;
    private String nickname;

    private String title;
    private String content;

    private MarketType marketType;
    private String itemName;
    private Integer price;
    private String condition;
    private String contact;

    private String location;
    private String countryCode;
    private String countryName;

    private LocalDateTime createdAt;

}
