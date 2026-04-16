package com.main.donghang.domain.market;

import com.main.donghang.domain.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "market_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MarketPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MarketType marketType; // SELL / BUY

    @Column
    private Integer price;

    @Column(length = 100)
    private String itemName;

    @Column(length = 100)
    private String condition; // 새상품 / 중고 등

    @Column(length = 100)
    private String contact;

    public MarketPost(
            Post post,
            MarketType marketType,
            Integer price,
            String itemName,
            String condition,
            String contact
    ) {
        this.post = post;
        this.marketType = marketType;
        this.price = price;
        this.itemName = itemName;
        this.condition = condition;
        this.contact = contact;
    }

    public void update(
            MarketType marketType,
            Integer price,
            String itemName,
            String condition,
            String contact
    ) {
        this.marketType = marketType;
        this.price = price;
        this.itemName = itemName;
        this.condition = condition;
        this.contact = contact;
    }
}
