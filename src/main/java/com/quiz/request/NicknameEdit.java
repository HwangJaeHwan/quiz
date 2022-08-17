package com.quiz.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class NicknameEdit {

    @NotEmpty(message = "변경할 닉네임을 입력해주세요.")
    private String nickname;

    public NicknameEdit(String nickname) {
        this.nickname = nickname;
    }
}
