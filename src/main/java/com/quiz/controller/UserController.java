package com.quiz.controller;

import com.quiz.auth.UserInfo;
import com.quiz.request.NicknameUpdate;
import com.quiz.request.PasswordUpdate;
import com.quiz.request.UserCreate;
import com.quiz.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    void register(@RequestBody @Valid UserCreate userCreate) {

        userService.save(userCreate);

    }

    @PostMapping("/passwordChange")
    void passwordChange(@AuthenticationPrincipal UserInfo userInfo, @RequestBody @Valid PasswordUpdate passwordUpdate) {

        userService.passwordChange(userInfo.getUser().getId(), passwordUpdate);

    }

    @PostMapping("/nicknameChange")
    void nicknameChange(@AuthenticationPrincipal UserInfo userInfo, @RequestBody @Valid NicknameUpdate nicknameUpdate) {

        userService.nicknameChange(userInfo.getUser().getId(), nicknameUpdate);

    }

}
