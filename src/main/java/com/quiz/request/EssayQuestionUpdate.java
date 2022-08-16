package com.quiz.request;


import com.quiz.domain.Quiz;
import com.quiz.domain.question.EssayQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class EssayQuestionUpdate{


    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private String hint;

    @NotBlank(message = "정답을 입력해주세요.")
    private String answer;

    public EssayQuestionUpdate(String content, String hint, String answer) {
        this.content = content;
        this.hint = hint;
        this.answer = answer;
    }
}
