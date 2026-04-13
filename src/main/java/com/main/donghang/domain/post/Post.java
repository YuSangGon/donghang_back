package com.main.donghang.domain.post;

import com.main.donghang.domain.common.BaseTimeEntity;
import com.main.donghang.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PostCategory category;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false, length = 100)
    private String location;

    @Column(name = "country_code", nullable = false, length = 10)
    private String countryCode;

    @Column(name = "country_name", nullable = false, length = 100)
    private String countryName;

    @Column(name = "view_cnt", nullable = false)
    private int viewCnt;

    public Post(
            User user,
            PostCategory category,
            String title,
            String content,
            String location,
            String countryCode,
            String countryName
    ) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.content = content;
        this.location = location;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.viewCnt = 0;
    }

    public void update(
            String title,
            String content,
            String location,
            PostCategory category,
            String countryCode,
            String countryName
    ) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.category = category;
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public void increaseViewCount() {
        this.viewCnt++;
    }
}
