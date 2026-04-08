package com.main.donghang.domain.post;

import com.main.donghang.domain.post.dto.*;
import com.main.donghang.domain.user.User;
import com.main.donghang.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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

    public List<PostSimpleResponse> getPostsByCategory(PostCategory category) {
        return postRepository.findByCategoryOrderByCreatedAtDesc(category)
                .stream()
                .map(PostSimpleResponse::new)
                .toList();
    }

    public List<HomePostPreviewResponse> getLatestTop8ByCategory(PostCategory category) {
        return postRepository.findTop8ByCategoryOrderByCreatedAtDesc(category)
                .stream()
                .map(HomePostPreviewResponse::new)
                .toList();
    }


}
