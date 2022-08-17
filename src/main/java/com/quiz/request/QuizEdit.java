package com.quiz.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class QuizEdit {


    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;


    public QuizEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
