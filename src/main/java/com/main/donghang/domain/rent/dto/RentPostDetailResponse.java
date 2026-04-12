package com.main.donghang.domain.rent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class RentPostDetailResponse {

    private Long postId;
    private String title;
    private String content;
    private String nickname;

    private RentOfferType offerType;

    private String address;
    private String placeId;
    private Double lat;
    private Double lng;

    private RentStayType stayType;

    private BigDecimal rentFee;
    private RentFeeUnit rentFeeUnit;
    private BigDecimal deposit;
    private LocalDate availableFrom;
    private String minimumStay;
    private String noticePeriod;

    private BigDecimal budget;
    private LocalDate preferredMoveInDate;
    private String preferredStayDuration;

    private List<String> imageUrls;
    private LocalDateTime createdAt;


}
