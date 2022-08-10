package com.quiz.request;


import com.quiz.domain.Quiz;
import com.quiz.domain.question.EssayQuestion;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EssayQuestionCreate {


    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private String hint;
    @NotBlank(message = "정답을 입력해주세요.")
    private String answer;


    public EssayQuestion makeEssayQuestion(Quiz quiz) {

        return EssayQuestion.builder()
                .content(content)
                .hint(hint)
                .answer(answer)
                .quiz(quiz)
                .build();

    }


}
