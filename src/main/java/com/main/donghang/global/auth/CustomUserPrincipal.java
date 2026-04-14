package com.main.donghang.global.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomUserPrincipal {

    private final Long userId;
    private final String loginId;

}
