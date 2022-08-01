package com.quiz.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CommentCreate {

    @NotEmpty(message = "댓글 내용을 채워주세요.")
    private String content;

    public CommentCreate(String content) {
        this.content = content;
    }
}
