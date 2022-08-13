package com.quiz.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public abstract class QuestionUpdate {

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private String hint;

    @NotBlank(message = "정답을 입력해주세요.")
    private String answer;


}
