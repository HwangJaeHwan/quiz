package com.quiz.request;

import com.quiz.domain.Quiz;
import com.quiz.domain.question.MultipleChoiceQuestion;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MultipleChoiceQuestionCreate {

    @NotNull
    private Integer number;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private String hint;

    @Size(min = 4, max = 4,message = "보기는 4개까지 생성해야합니다.")
    private List<String> examples;

    @NotBlank(message = "정답을 입력해주세요.")
    private String answer;


    public MultipleChoiceQuestion makeMultipleChoiceQuestion(Quiz quiz) {

        return MultipleChoiceQuestion.builder()
                .number(number)
                .content(content)
                .hint(hint)
                .answer(answer)
                .examples(examples)
                .quiz(quiz)
                .build();


    }



}
