package com.main.donghang.domain.market;

import com.main.donghang.domain.market.dto.MarketPostCreateRequest;
import com.main.donghang.domain.market.dto.MarketPostDetailResponse;
import com.main.donghang.domain.market.dto.MarketPostUpdateRequest;
import com.main.donghang.domain.post.Post;
import com.main.donghang.domain.post.PostCategory;
import com.main.donghang.domain.post.PostRepository;
import com.main.donghang.domain.user.User;
import com.main.donghang.domain.user.UserRepository;
import com.main.donghang.global.auth.AuthUserUtil;
import jakarta.persistence.Table;
import org.springframework.stereotype.Service;

@Service
@Table
public class MarketPostService {

    private final PostRepository postRepository;
    private final MarketPostRepository marketPostRepository;
    private final UserRepository userRepository;
    private final AuthUserUtil authUserUtil;

    public MarketPostService(
            PostRepository postRepository,
            MarketPostRepository marketPostRepository,
            UserRepository userRepository,
            AuthUserUtil authUserUtil
    ) {
        this.postRepository = postRepository;
        this.marketPostRepository = marketPostRepository;
        this.userRepository = userRepository;
        this.authUserUtil = authUserUtil;
    }

    public Long create(MarketPostCreateRequest request) {
        Long userId = authUserUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow();

        Post post = new Post(
                user,
                PostCategory.MARKET,
                request.getTitle(),
                request.getContent(),
                request.getLocation(),
                request.getCountryCode(),
                request.getCountryName()
        );

        postRepository.save(post);

        MarketPost marketPost = new MarketPost(
                post,
                request.getMarketType(),
                request.getPrice(),
                request.getItemName(),
                request.getCondition(),
                request.getContact()
        );

        marketPostRepository.save(marketPost);

        return post.getId();
    }

    public Long update(Long postId, MarketPostUpdateRequest request) {
        MarketPost marketPost = marketPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 중고 게시글입니다. postId=" + postId));

        Post post = marketPost.getPost();
        validateOwner(post);

        post.update(
                request.getTitle(),
                request.getContent(),
                request.getLocation(),
                PostCategory.MARKET,
                request.getCountryCode(),
                request.getCountryName()
        );

        marketPost.update(
                request.getMarketType(),
                request.getPrice(),
                request.getItemName(),
                request.getCondition(),
                request.getContact()
        );

        return postId;
    }

    public MarketPostDetailResponse getDetail(Long postId) {
        MarketPost marketPost = marketPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 중고 게시글입니다. postId=" + postId));

        Post post = marketPost.getPost();
        post.increaseViewCount();

        return new MarketPostDetailResponse(
                post.getId(),
                post.getUser().getId(),
                post.getUser().getNickname(),
                post.getTitle(),
                post.getContent(),
                marketPost.getMarketType(),
                marketPost.getItemName(),
                marketPost.getPrice(),
                marketPost.getCondition(),
                marketPost.getContact(),
                post.getLocation(),
                post.getCountryCode(),
                post.getCountryName(),
                post.getCreatedAt()
        );
    }

    private void validateOwner(Post post) {
        Long currentUserId = authUserUtil.getCurrentUserId();

        if (!post.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("본인이 작성한 글만 수정할 수 있습니다.");
        }
    }

}
