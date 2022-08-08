package com.quiz.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class UserCreate {

    @NotBlank(message = "아이디를 입력해주세요")
    private String loginId;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    private String passwordCheck;
    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
    @Builder
    public UserCreate(String loginId, String password, String passwordCheck, String nickname, String email) {
        this.loginId = loginId;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.nickname = nickname;
        this.email = email;
    }
}
