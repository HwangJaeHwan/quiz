package com.quiz.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class UserCreate {

    @NotEmpty(message = "아이디를 입력해주세요")
    private String loginId;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;
    @NotEmpty(message = "비밀번호 확인을 입력해주세요")
    private String passwordCheck;
    @NotEmpty(message = "닉네임을 입력해주세요")
    private String nickname;
    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;

}
