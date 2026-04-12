package com.main.donghang.location;

import com.main.donghang.location.dto.LocationSuggestionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

@Service
public class LocationService {

    private final RestClient restClient;
    private final String geoNamesUsername;

    public LocationService(
            RestClient restClient,
            @Value("${geonames.username}") String geoNamesUsername
    ) {
        this.restClient = restClient;
        this.geoNamesUsername = geoNamesUsername;
    }

    public List<LocationSuggestionResponse> suggest(String query) {
        if ( query == null || query.trim().length() < 1) {
            return Collections.emptyList();
        }

        GeoNamesSearchResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("api.geonames.org")
                        .path("/searchJSON")
                        .queryParam("q", query.trim())
                        .queryParam("maxRows", 10)
                        .queryParam("featureClass", "A")
                        .queryParam("featureClass", "P")
                        .queryParam("orderby", "relevance")
                        .queryParam("username", "yusanggon")
                        .build())
                .retrieve()
                .body(GeoNamesSearchResponse.class);

        if(response == null || response.geonames() == null) {
            return Collections.emptyList();
        }

        return response.geonames().stream()
                .map(item -> {
                    String displayName = buildDisplayName(
                            item.name(),
                            item.countryName()
                    );
                    return new LocationSuggestionResponse(
                            item.name(),
                            item.countryName(),
                            displayName
                    );
                })
                .distinct()
                .toList();

    }

    private String buildDisplayName(String name, String countryName) {
        if (countryName == null || countryName.isBlank() || countryName.equals(name)) {
            return name;
        }
        return name + ", " + countryName;
    }

    private record GeoNamesSearchResponse(List<GeoNameItem> geonames) {}
    private record GeoNameItem(
            String name,
            String countryName
    ) {}

}
