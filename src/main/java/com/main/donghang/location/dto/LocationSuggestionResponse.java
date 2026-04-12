package com.main.donghang.location.dto;

import lombok.Getter;

@Getter
public class LocationSuggestionResponse {

    private final String name;
    private final String countryName;
    private final String displayName;

    public LocationSuggestionResponse(String name, String countryName, String displayName) {
        this.name = name;
        this.countryName = countryName;
        this.displayName = displayName;
    }

}
