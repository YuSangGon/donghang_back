package com.main.donghang.domain.rent;

import com.main.donghang.domain.rent.dto.RentPostCreateRequest;
import com.main.donghang.domain.rent.dto.RentPostDetailResponse;
import com.main.donghang.domain.rent.dto.RentPostUpdateRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rent-posts")
public class RentPostController {

    private final RentPostService rentPostService;

    public RentPostController(RentPostService rentPostService) {
        this.rentPostService = rentPostService;
    }

    @PostMapping
    public Long create(@RequestBody RentPostCreateRequest request) {
        return rentPostService.create(request);
    }

    @GetMapping("/{postId}")
    public RentPostDetailResponse getDetail(@PathVariable Long postId) {
        return rentPostService.getDetail(postId);
    }

    @PutMapping("/{postId}")
    public Long update(
            @PathVariable Long postId,
            @RequestBody RentPostUpdateRequest request
    ) {
        return rentPostService.update(postId, request);
    }

}
