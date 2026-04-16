package com.main.donghang.domain.market;

import com.main.donghang.domain.market.dto.MarketPostCreateRequest;
import com.main.donghang.domain.market.dto.MarketPostDetailResponse;
import com.main.donghang.domain.market.dto.MarketPostUpdateRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market-posts")
public class MarketPostController {

    private final MarketPostService marketPostService;

    public MarketPostController(MarketPostService marketPostService) {
        this.marketPostService = marketPostService;
    }

    @PostMapping
    public Long create(@RequestBody MarketPostCreateRequest request) {
        return marketPostService.create(request);
    }

    @PutMapping("/{postId}")
    public Long update(
            @PathVariable Long postId,
            @RequestBody MarketPostUpdateRequest request
    ) {
        return marketPostService.update(postId, request);
    }

    @GetMapping("/{postId}")
    public MarketPostDetailResponse getDetail(@PathVariable Long postId) {
        return marketPostService.getDetail(postId);
    }

}
