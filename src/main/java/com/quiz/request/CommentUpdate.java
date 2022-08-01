package com.quiz.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CommentUpdate {

    @NotEmpty(message = "댓글 수정은 공백일 수 없습니다.")
    private String content;

    public CommentUpdate(String content) {
        this.content = content;
    }
}
