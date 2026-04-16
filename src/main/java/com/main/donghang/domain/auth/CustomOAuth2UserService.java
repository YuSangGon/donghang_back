package com.main.donghang.domain.auth;

import com.main.donghang.domain.user.Gender;
import com.main.donghang.domain.user.User;
import com.main.donghang.domain.user.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        if(!"google".equals(registrationId)) {
            return oAuth2User;
        }

        String providerId = String.valueOf(attributes.get("sub"));
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        userRepository.findByProviderAndProviderId("GOOGLE", providerId)
                .or(() -> userRepository.findByEmail(email))
                .orElseGet(() -> {
                    String generatedLoginId = "google_" + UUID.randomUUID().toString().substring(0,8);
                    String nickname = generateUniqueNickname(name != null ? name : "googleUser");

                    User newUser = new User(
                            generatedLoginId,
                            "",
                            email,
                            nickname,
                            Gender.MALE,
                            "GOOGLE",
                            providerId
                    );

                    return userRepository.save(newUser);
                });
        return oAuth2User;
    }

    private String generateUniqueNickname(String base) {
        String nickname = base.replaceAll("\\s+", "");
        if (nickname.isBlank()) {
            nickname = "googleUser";
        }

        String candidate = nickname;
        int suffix = 1;

        while (userRepository.existsByNickname(candidate)) {
            candidate = nickname + suffix;
            suffix++;
        }

        return candidate;
    }
}
