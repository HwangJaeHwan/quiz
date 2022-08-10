package com.quiz.request;

import com.quiz.domain.Quiz;
import com.quiz.domain.question.MultipleChoiceQuestion;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MultipleChoiceQuestionCreate {


    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private String hint;

    @Size(min = 2, max = 4,message = "보기는 2~4개까지 생성가능합니다.")
    private List<String> examples;

    @NotBlank(message = "정답을 입력해주세요.")
    private String answer;


    public MultipleChoiceQuestion makeMultipleChoiceQuestion(Quiz quiz) {

        return MultipleChoiceQuestion.builder()
                .content(content)
                .hint(hint)
                .answer(answer)
                .quiz(quiz)
                .build();


    }



}
