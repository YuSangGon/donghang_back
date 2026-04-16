package com.main.donghang.domain.market;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketPostRepository extends JpaRepository<MarketPost, Long> {
    Optional<MarketPost> findByPostId(Long postId);
}
