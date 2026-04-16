package com.main.donghang.domain.market.dto;

import com.main.donghang.domain.market.MarketType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MarketPostCreateRequest {
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
}
