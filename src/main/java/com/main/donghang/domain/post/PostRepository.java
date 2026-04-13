package com.main.donghang.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    List<Post> findTop8ByCategoryOrderByCreatedAtDesc(PostCategory category);

    Page<Post> findByCategoryOrderByCreatedAtDesc(PostCategory category, Pageable pageable);
}
