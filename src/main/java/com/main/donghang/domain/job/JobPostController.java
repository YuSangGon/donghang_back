package com.main.donghang.domain.job;

import com.main.donghang.domain.job.dto.JobPostCreateRequest;
import com.main.donghang.domain.job.dto.JobPostDetailResponse;
import com.main.donghang.domain.job.dto.JobPostUpdateRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-posts")
public class JobPostController {

    private final JobPostService jobPostService;

    public JobPostController(JobPostService jobPostService) {
        this.jobPostService = jobPostService;
    }

    @PostMapping
    public Long create(@RequestBody JobPostCreateRequest request) {
        return jobPostService.create(request);
    }

    @PutMapping("/{postId}")
    public Long update(
            @PathVariable Long postId,
            @RequestBody JobPostUpdateRequest request
    ) {
        return jobPostService.update(postId, request);
    }

    @GetMapping("/{postId}")
    public JobPostDetailResponse getDetail(@PathVariable Long postId) {
        return jobPostService.getDetail(postId);
    }

}
