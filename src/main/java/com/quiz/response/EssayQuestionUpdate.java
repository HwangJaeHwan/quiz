package com.quiz.response;


import com.quiz.domain.Quiz;
import com.quiz.domain.question.EssayQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
public class EssayQuestionUpdate extends QuestionUpdate {


    public EssayQuestionUpdate(String content, String hint, String answer) {
        super(content, hint, answer);
    }
}
