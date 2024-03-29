package com.quiz.auth;

import com.quiz.auth.oauth.GoogleUserInfo;
import com.quiz.auth.oauth.KakaoUserInfo;
import com.quiz.auth.oauth.NaverUserInfo;
import com.quiz.auth.oauth.OAuth2UserInfo;
import com.quiz.domain.User;
import com.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserOauthService extends DefaultOAuth2UserService {


    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;
        Random random = new Random();

        log.info("attributes = {}",oAuth2User.getAttributes());


        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttribute("response"));
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }
        log.info("kakao = {}",oAuth2UserInfo);

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String password = encoder.encode(UUID.randomUUID().toString().substring(0, 10));
        String email = oAuth2UserInfo.getEmail();
        String role = "USER";
        String nickname = "tmp_";

        int tmp = random.nextInt();


        Optional<User> optional = userRepository.findByUsername(username);

        while (userRepository.findByNickname(nickname + tmp).isPresent()) {
            tmp = random.nextInt();
        }


        if (!optional.isPresent()) {

            log.info("최초 로그인");

            User user = User.builder()
                    .username(username)
                    .password(password)
                    .nickname(nickname + tmp)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

            userRepository.save(user);

            return new UserInfo(user, oAuth2User.getAttributes());


        }
        log.info("이미 가입");

        return new UserInfo(optional.get(), oAuth2User.getAttributes());
    }


}
