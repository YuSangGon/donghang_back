package com.main.donghang.domain.job;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    Optional<JobPost> findByPostId(Long postId);

}
