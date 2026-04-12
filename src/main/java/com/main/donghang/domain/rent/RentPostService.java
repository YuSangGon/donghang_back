package com.main.donghang.domain.rent;

import com.main.donghang.domain.post.Post;
import com.main.donghang.domain.post.PostCategory;
import com.main.donghang.domain.post.PostRepository;
import com.main.donghang.domain.rent.dto.RentPostCreateRequest;
import com.main.donghang.domain.rent.dto.RentPostDetailResponse;
import com.main.donghang.domain.rent.dto.RentPostUpdateRequest;
import com.main.donghang.domain.user.User;
import com.main.donghang.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class RentPostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final RentPostRepository rentPostRepository;
    private final PostImageRepository postImageRepository;

    public RentPostService(
            UserRepository userRepository,
            PostRepository postRepository,
            RentPostRepository rentPostRepository,
            PostImageRepository postImageRepository
    ) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.rentPostRepository = rentPostRepository;
        this.postImageRepository = postImageRepository;
    }

    public Long create(RentPostCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. id=" + request.getUserId()));

        Post post = new Post(
                user,
                PostCategory.RENT,
                request.getTitle(),
                request.getContent(),
                request.getAddress()
        );

        Post savedPost = postRepository.save(post);

        RentPost rentPost = new RentPost(
                savedPost,
                request.getOfferType(),
                request.getAddress(),
                request.getPlaceId(),
                request.getLat(),
                request.getLng(),
                request.getStayType(),
                request.getRentFee(),
                request.getRentFeeUnit(),
                request.getDeposit(),
                request.getAvailableFrom(),
                request.getMinimumStay(),
                request.getNoticePeriod(),
                request.getBudget(),
                request.getPreferredMoveInDate(),
                request.getPreferredStayDuration()
        );

        rentPostRepository.save(rentPost);

        List<String> imageUrls = request.getImageUrls() == null
                ? Collections.emptyList()
                : request.getImageUrls();

        for (int i = 0; i < imageUrls.size(); i++) {
            postImageRepository.save(new PostImage(savedPost, imageUrls.get(i), i));
        }

        return savedPost.getId();
    }

    public RentPostDetailResponse getDetail(Long postId) {
        RentPost rentPost = rentPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 렌트 게시글입니다. postId=" + postId));

        Post post = rentPost.getPost();
        post.increaseViewCount();

        List<String> imageUrls = postImageRepository.findByPostIdOrderBySortOrderAsc(postId)
                .stream()
                .map(PostImage::getImageUrl)
                .toList();

        return new RentPostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),
                rentPost.getOfferType(),
                rentPost.getAddress(),
                rentPost.getPlaceId(),
                rentPost.getLat(),
                rentPost.getLng(),
                rentPost.getStayType(),
                rentPost.getRentFee(),
                rentPost.getRentFeeUnit(),
                rentPost.getDeposit(),
                rentPost.getAvailableFrom(),
                rentPost.getMinimumStay(),
                rentPost.getNoticePeriod(),
                rentPost.getBudget(),
                rentPost.getPreferredMoveInDate(),
                rentPost.getPreferredStayDuration(),
                imageUrls,
                post.getCreatedAt()
        );
    }

    public Long update(Long postId, RentPostUpdateRequest request) {
        RentPost rentPost = rentPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 렌트 게시글입니다. postId=" + postId));

        Post post = rentPost.getPost();
        post.update(
                request.getTitle(),
                request.getContent(),
                request.getAddress(),
                PostCategory.RENT
        );

        rentPost.update(
                request.getOfferType(),
                request.getAddress(),
                request.getPlaceId(),
                request.getLat(),
                request.getLng(),
                request.getStayType(),
                request.getRentFee(),
                request.getRentFeeUnit(),
                request.getDeposit(),
                request.getAvailableFrom(),
                request.getMinimumStay(),
                request.getNoticePeriod(),
                request.getBudget(),
                request.getPreferredMoveInDate(),
                request.getPreferredStayDuration()
        );

        postImageRepository.deleteByPostId(postId);

        List<String> imageUrls = request.getImageUrls() == null ? Collections.emptyList() : request.getImageUrls();
        for (int i = 0; i < imageUrls.size(); i++) {
            postImageRepository.save(new PostImage(post, imageUrls.get(i), i));
        }

        return postId;
    }

}
