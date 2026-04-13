package com.main.donghang.domain.job;

import com.main.donghang.domain.job.dto.JobPostCreateRequest;
import com.main.donghang.domain.job.dto.JobPostDetailResponse;
import com.main.donghang.domain.job.dto.JobPostUpdateRequest;
import com.main.donghang.domain.post.Post;
import com.main.donghang.domain.post.PostCategory;
import com.main.donghang.domain.post.PostRepository;
import com.main.donghang.domain.user.User;
import com.main.donghang.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JobPostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JobPostRepository jobPostRepository;

    public JobPostService(
            UserRepository userRepository,
            PostRepository postRepository,
            JobPostRepository jobPostRepository
    ) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.jobPostRepository = jobPostRepository;
    }

    public Long create(JobPostCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. id=" + request.getUserId()));

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구직 게시글입니다. postId=" + postId));

        Post post = jobPost.getPost();
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구직 게시글입니다. postId=" + postId));

        Post post = jobPost.getPost();
        post.increaseViewCount();

        return new JobPostDetailResponse(
                post.getId(),
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
