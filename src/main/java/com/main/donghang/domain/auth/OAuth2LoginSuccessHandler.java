package com.main.donghang.domain.auth;

import com.main.donghang.domain.user.User;
import com.main.donghang.domain.user.UserRepository;
import com.main.donghang.global.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final String frontendRedirectUri;

    public OAuth2LoginSuccessHandler(
            UserRepository userRepository,
            JwtTokenProvider jwtTokenProvider,
            @Value("${app.oauth2.redirect-uri}") String frontendRedirectUri
    ) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.frontendRedirectUri = frontendRedirectUri;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        String providerId = String.valueOf(oauth2User.getAttributes().get("sub"));
        String email = (String) oauth2User.getAttributes().get("email");

        User user = userRepository.findByProviderAndProviderId("GOOGLE", providerId)
                .or(() -> userRepository.findByEmail(email))
                .orElseThrow(() -> new IllegalArgumentException("Google 로그인 사용자 정보를 찾을 수 없습니다."));

        String accessToken = jwtTokenProvider.createToken(user.getId(), user.getLoginId());

        String redirectUrl = frontendRedirectUri
                + "?token=" + URLEncoder.encode(accessToken, StandardCharsets.UTF_8)
                + "&userId=" + user.getId()
                + "&loginId=" + URLEncoder.encode(user.getLoginId(), StandardCharsets.UTF_8)
                + "&nickname=" + URLEncoder.encode(user.getNickname(), StandardCharsets.UTF_8);

        response.sendRedirect(redirectUrl);
    }
}
