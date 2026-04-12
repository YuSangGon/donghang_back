package com.main.donghang.location;

import com.main.donghang.location.dto.LocationSuggestionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/api/locations/suggest")
    public List<LocationSuggestionResponse> suggest(
            @RequestParam("q") String query
    ) {
        return locationService.suggest(query);
    }
}
