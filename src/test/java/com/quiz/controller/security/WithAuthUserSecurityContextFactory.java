package com.quiz.controller.security;

import com.quiz.auth.UserInfoService;
import com.quiz.domain.User;
import com.quiz.repository.UserRepository;
import com.quiz.request.UserCreate;
import com.quiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;


@RequiredArgsConstructor
public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {


    private final UserService userService;
    private final UserInfoService userInfoService;


    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {

        String username = annotation.username();
        String password = "password";
        String email = username + "@naver.com";
        String nickname = "nickname";

        UserCreate userCreate = UserCreate.builder()
                .loginId(username)
                .password(password)
                .passwordCheck(password)
                .nickname(nickname)
                .email(email)
                .build();

        userService.save(userCreate);

        UserDetails userDetails = userInfoService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        return securityContext;
    }




}
