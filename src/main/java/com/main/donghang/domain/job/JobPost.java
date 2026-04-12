package com.main.donghang.domain.job;

import com.main.donghang.domain.job.dto.JobType;
import com.main.donghang.domain.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "job_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false, length = 20)
    private JobType jobType;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal pay;

    @Column(nullable = false, length = 100)
    private String position;

    public JobPost(Post post, JobType jobType, BigDecimal pay, String position) {
        this.post = post;
        this.jobType = jobType;
        this.pay = pay;
        this.position = position;
    }

    public void update(JobType jobType, BigDecimal pay, String position) {
        this.jobType = jobType;
        this.pay = pay;
        this.position = position;
    }

}
