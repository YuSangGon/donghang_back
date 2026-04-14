package com.main.donghang.domain.post;

import com.main.donghang.domain.job.JobPostRepository;
import com.main.donghang.domain.post.dto.*;
import com.main.donghang.domain.rent.RentPostRepository;
import com.main.donghang.domain.user.User;
import com.main.donghang.domain.user.UserRepository;
import com.main.donghang.global.auth.AuthUserUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final RentPostRepository rentPostRepository;
    private final JobPostRepository jobPostRepository;
    private final AuthUserUtil authUserUtil;

    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            RentPostRepository rentPostRepository,
            JobPostRepository jobPostRepository,
            AuthUserUtil authUserUtil
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.rentPostRepository = rentPostRepository;
        this.jobPostRepository = jobPostRepository;
        this.authUserUtil = authUserUtil;
    }

    public Long create(PostCreateRequest request) {
        Long currentUserId = authUserUtil.getCurrentUserId();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Post post = new Post(
                user,
                request.getCategory(),
                request.getTitle(),
                request.getContent(),
                request.getLocation(),
                request.getCountryCode(),
                request.getCountryName()
        );

        return postRepository.save(post).getId();
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        post.increaseViewCount();

        return new PostResponse(post);
    }

    private void validateOwner(Post post) {
        Long currentUserId = authUserUtil.getCurrentUserId();

        if (!post.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("본인이 작성한 글만 수정 또는 삭제할 수 있습니다.");
        }
    }

    public Long update(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        validateOwner(post);

        post.update(
                request.getTitle(),
                request.getContent(),
                request.getLocation(),
                request.getCategory(),
                request.getCountryCode(),
                request.getCountryName()
        );

        return post.getId();
    }

    public void delete(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        validateOwner(post);

        postRepository.delete(post);
    }

    public PostPageResponse getPostsByCategory(
            PostCategory category,
            String countryCode,
            String keyword,
            int page,
            int size
    ) {

        Specification<Post> specification = Specification
                .where(PostSpecification.hasCategory(category))
                .and(PostSpecification.hasCountryCode(countryCode))
                .and(PostSpecification.containsKeyword(keyword));

        Page<Post> result = postRepository.findAll(specification, PageRequest.of(page, size));

        List<PostSimpleResponse> content = result.getContent()
                .stream()
                .map(post -> {
                    if (post.getCategory() == PostCategory.RENT) {
                        return rentPostRepository.findByPostId(post.getId())
                                .map(rentPost -> new PostSimpleResponse(post, rentPost.getOfferType()))
                                .orElseGet(() -> new PostSimpleResponse(post));
                    }

                    if (post.getCategory() == PostCategory.JOB) {
                        return jobPostRepository.findByPostId(post.getId())
                                .map(jobPost -> new PostSimpleResponse(post, jobPost.getJobType()))
                                .orElseGet(() -> new PostSimpleResponse(post));
                    }
                    return new PostSimpleResponse(post);
                })
                .toList();

        return new PostPageResponse(
                content,
                result.getNumber(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.isFirst(),
                result.isLast()
        );
    }

    public List<HomePostPreviewResponse> getLatestTop8ByCategory(PostCategory category) {
        return postRepository.findTop8ByCategoryOrderByCreatedAtDesc(category)
                .stream()
                .map(HomePostPreviewResponse::new)
                .toList();
    }


}
