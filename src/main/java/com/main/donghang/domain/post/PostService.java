package com.main.donghang.domain.post;

import com.main.donghang.domain.post.dto.*;
import com.main.donghang.domain.rent.RentPostRepository;
import com.main.donghang.domain.user.User;
import com.main.donghang.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final RentPostRepository rentPostRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, RentPostRepository rentPostRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.rentPostRepository = rentPostRepository;
    }

    public Long create(PostCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Post post = new Post(
                user,
                request.getCategory(),
                request.getTitle(),
                request.getContent(),
                request.getLocation()
        );

        return postRepository.save(post).getId();
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        post.increaseViewCount();

        return new PostResponse(post);
    }

    public Long update(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        post.update(
                request.getTitle(),
                request.getContent(),
                request.getLocation(),
                request.getCategory()
        );

        return post.getId();
    }

    public void delete(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        postRepository.delete(post);
    }

    public PostPageResponse getPostsByCategory(PostCategory category, int page, int size) {
        Page<Post> result = postRepository.findByCategoryOrderByCreatedAtDesc(category, PageRequest.of(page, size));

        List<PostSimpleResponse> content = result.getContent()
                .stream()
                .map(post -> {
                    if (post.getCategory() == PostCategory.RENT) {
                        return rentPostRepository.findByPostId(post.getId())
                                .map(rentPost -> new PostSimpleResponse(post, rentPost.getOfferType()))
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
