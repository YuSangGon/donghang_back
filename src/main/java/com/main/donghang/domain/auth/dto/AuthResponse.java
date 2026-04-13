package com.main.donghang.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthResponse {

    private String accessToken;
    private Long userId;
    private String loginId;
    private String nickname;

}
