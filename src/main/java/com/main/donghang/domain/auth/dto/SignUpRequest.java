package com.main.donghang.domain.auth.dto;

import com.main.donghang.domain.user.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignUpRequest {

    @NotBlank
    @Size(min = 4, max = 20)
    @Pattern(
            regexp = "^[A-Za-z0-9]+$",
            message = "아이디는 4~20자이며, 영문+숫자만 가능(공백 및 특수문자X)"
    )
    private String loginId;

    @NotBlank
    @Size(min = 4, max = 50)
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[~`!@#$%^&*()\\-+=])(?=.*[A-Za-z].*[A-Za-z]).{8,50}$",
            message = "비밀번호는 8~50자이며, 영문 2자 이상/숫자/특수문자를 모두 포함해야 합니다."
    )
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 20)
    private String nickname;

    private Gender gender;


}
