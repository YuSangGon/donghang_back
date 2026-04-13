package com.main.donghang.domain.post;

import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {

    public static Specification<Post> hasCategory(PostCategory category) {
        return ( root, query, cb )
                -> cb.equal(root.get("category"), category);
    }

    public static Specification<Post> hasCountryCode(String countryCode) {
        return (root, query, cb) -> {
            if ( countryCode == null || countryCode.isBlank() ) {
                return cb.conjunction();
            }
            return cb.equal(root.get("countryCode"), countryCode);
        };
    }

    public static Specification<Post> containsKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();
            }

            String likeKeyword = "%" + keyword.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("title")), likeKeyword),
                    cb.like(cb.lower(root.get("content")), likeKeyword),
                    cb.like(cb.lower(root.get("location")), likeKeyword),
                    cb.like(cb.lower(root.get("countryName")), likeKeyword)
            );
        };
    }

}
