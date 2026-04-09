package com.main.donghang.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop8ByCategoryOrderByCreatedAtDesc(PostCategory category);

    Page<Post> findByCategoryOrderByCreatedAtDesc(PostCategory category, Pageable pageable);
}
