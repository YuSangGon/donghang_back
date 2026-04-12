package com.main.donghang.domain.rent;

import com.main.donghang.domain.post.Post;
import com.main.donghang.domain.rent.dto.RentFeeUnit;
import com.main.donghang.domain.rent.dto.RentOfferType;
import com.main.donghang.domain.rent.dto.RentStayType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rent_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RentPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "offer_type", nullable = false, length = 20)
    private RentOfferType offerType;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(name = "place_id", length = 255)
    private String placeId;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;

    @Enumerated(EnumType.STRING)
    @Column(name = "stay_type", nullable = false, length = 20)
    private RentStayType stayType;

    @Column(name = "rent_fee", precision = 12, scale = 2)
    private BigDecimal rentFee;

    @Enumerated(EnumType.STRING)
    @Column(name = "rent_fee_unit", length = 20)
    private RentFeeUnit rentFeeUnit;

    @Column(precision = 12, scale = 2)
    private BigDecimal deposit;

    @Column(name = "available_from")
    private LocalDate availableFrom;

    @Column(name = "minimum_stay", length = 100)
    private String minimumStay;

    @Column(name = "notice_period", length = 100)
    private String noticePeriod;

    @Column(precision = 12, scale = 2)
    private BigDecimal budget;

    @Column(name = "preferred_move_in_date")
    private LocalDate preferredMoveInDate;

    @Column(name = "preferred_stay_duration", length = 100)
    private String preferredStayDuration;

    public RentPost(
            Post post,
            RentOfferType offerType,
            String address,
            String placeId,
            Double lat,
            Double lng,
            RentStayType stayType,
            BigDecimal rentFee,
            RentFeeUnit rentFeeUnit,
            BigDecimal deposit,
            LocalDate availableFrom,
            String minimumStay,
            String noticePeriod,
            BigDecimal budget,
            LocalDate preferredMoveInDate,
            String preferredStayDuration
    ) {
        this.post = post;
        this.offerType = offerType;
        this.address = address;
        this.placeId = placeId;
        this.lat = lat;
        this.lng = lng;
        this.stayType = stayType;
        this.rentFee = rentFee;
        this.rentFeeUnit = rentFeeUnit;
        this.deposit = deposit;
        this.availableFrom = availableFrom;
        this.minimumStay = minimumStay;
        this.noticePeriod = noticePeriod;
        this.budget = budget;
        this.preferredMoveInDate = preferredMoveInDate;
        this.preferredStayDuration = preferredStayDuration;
    }

    public void update(
            RentOfferType offerType,
            String address,
            String placeId,
            Double lat,
            Double lng,
            RentStayType stayType,
            BigDecimal rentFee,
            RentFeeUnit rentFeeUnit,
            BigDecimal deposit,
            LocalDate availableFrom,
            String minimumStay,
            String noticePeriod,
            BigDecimal budget,
            LocalDate preferredMoveInDate,
            String preferredStayDuration
    ) {
        this.offerType = offerType;
        this.address = address;
        this.placeId = placeId;
        this.lat = lat;
        this.lng = lng;
        this.stayType = stayType;
        this.rentFee = rentFee;
        this.rentFeeUnit = rentFeeUnit;
        this.deposit = deposit;
        this.availableFrom = availableFrom;
        this.minimumStay = minimumStay;
        this.noticePeriod = noticePeriod;
        this.budget = budget;
        this.preferredMoveInDate = preferredMoveInDate;
        this.preferredStayDuration = preferredStayDuration;
    }
}