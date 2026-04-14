package com.main.donghang.domain.rent.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
public class RentPostCreateRequest {
    private String title;
    private String content;

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
    private String countryCode;
    private String countryName;

}
