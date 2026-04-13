package com.main.donghang.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

}
