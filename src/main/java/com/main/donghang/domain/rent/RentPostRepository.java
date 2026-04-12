package com.main.donghang.domain.rent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentPostRepository extends JpaRepository<RentPost, Long> {

    Optional<RentPost> findByPostId(Long postId);

}
