package com.main.donghang.domain.auth.dto;

import com.main.donghang.domain.user.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignUpRequest {

    @NotBlank
    @Size(min = 4, max = 20)
    private String loginId;

    @Size(min = 4, max = 100)
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 20)
    private String nickname;

    private Gender gender;


}
