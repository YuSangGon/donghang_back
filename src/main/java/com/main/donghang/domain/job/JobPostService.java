package com.main.donghang.domain.job;

import com.main.donghang.domain.job.dto.JobPostCreateRequest;
import com.main.donghang.domain.job.dto.JobPostDetailResponse;
import com.main.donghang.domain.job.dto.JobPostUpdateRequest;
import com.main.donghang.domain.post.Post;
import com.main.donghang.domain.post.PostCategory;
import com.main.donghang.domain.post.PostRepository;
import com.main.donghang.domain.user.User;
import com.main.donghang.domain.user.UserRepository;
import com.main.donghang.global.auth.AuthUserUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JobPostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JobPostRepository jobPostRepository;
    private final AuthUserUtil authUserUtil;

    public JobPostService(
            UserRepository userRepository,
            PostRepository postRepository,
            JobPostRepository jobPostRepository,
            AuthUserUtil authUserUtil
    ) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.jobPostRepository = jobPostRepository;
        this.authUserUtil = authUserUtil;
    }

    private void validateOwner(Post post) {
        Long currentUserId = authUserUtil.getCurrentUserId();
        if (!post.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("본인이 작성한 글만 수정 또는 삭제할 수 있습니다.");
        }
    }

    public Long create(JobPostCreateRequest request) {
        Long currentUserId = authUserUtil.getCurrentUserId();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Post post = new Post(
                user,
                PostCategory.JOB,
                request.getTitle(),
                request.getContent(),
                request.getLocation(),
                request.getCountryCode(),
                request.getCountryName()
        );

        Post savedPost = postRepository.save(post);

        JobPost jobPost = new JobPost(
                savedPost,
                request.getJobType(),
                request.getPay(),
                request.getPosition()
        );

        jobPostRepository.save(jobPost);

        return savedPost.getId();
    }

    public Long update(Long postId, JobPostUpdateRequest request) {
        JobPost jobPost = jobPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구직 게시글입니다."));

        Post post = jobPost.getPost();
        validateOwner(post);
        post.update(
                request.getTitle(),
                request.getContent(),
                request.getLocation(),
                PostCategory.JOB,
                request.getCountryCode(),
                request.getCountryName()
        );

        jobPost.update(
                request.getJobType(),
                request.getPay(),
                request.getPosition()
        );

        return postId;
    }

    public JobPostDetailResponse getDetail(Long postId) {
        JobPost jobPost = jobPostRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구직 게시글입니다."));

        Post post = jobPost.getPost();
        post.increaseViewCount();

        return new JobPostDetailResponse(
                post.getId(),
                post.getUser().getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),
                post.getLocation(),
                jobPost.getJobType(),
                jobPost.getPay(),
                jobPost.getPosition(),
                post.getCreatedAt()
        );
    }

}
