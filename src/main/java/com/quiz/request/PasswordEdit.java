package com.quiz.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class PasswordEdit {

    @NotEmpty(message = "현재 비밀번호를 입력해주세요.")
    private String nowPassword;

    @NotEmpty(message = "바꿀 비밀번호를 입력해주세요.")
    private String changePassword;

    @NotEmpty(message = "비밀번호 확인을 입력해주세요.")
    private String passwordCheck;

    public PasswordEdit(String nowPassword, String changePassword, String passwordCheck) {
        this.nowPassword = nowPassword;
        this.changePassword = changePassword;
        this.passwordCheck = passwordCheck;
    }
}
