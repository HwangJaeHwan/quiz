package com.quiz.auth;

import com.quiz.domain.User;
import com.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserOauthService extends DefaultOAuth2UserService {


    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String password = encoder.encode(UUID.randomUUID().toString().substring(0, 10));
        String email = oAuth2User.getAttribute("email");
        String role = "USER";

        Optional<User> optional = userRepository.findByUsername(username);

        if (!optional.isPresent()) {

            User user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

            userRepository.save(user);

            return new UserInfo(user, oAuth2User.getAttributes());


        }

        return new UserInfo(optional.get(), oAuth2User.getAttributes());
    }


}
