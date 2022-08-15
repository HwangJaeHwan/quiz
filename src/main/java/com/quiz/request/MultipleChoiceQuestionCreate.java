package com.quiz.request;

import com.quiz.domain.Quiz;
import com.quiz.domain.question.MultipleChoiceQuestion;
import com.sun.istack.NotNull;
import lombok.*;

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

    @Size(min = 4, max = 4,message = "보기는 4개 생성해야합니다.")
    private List<String> examples;

    @NotBlank(message = "정답을 입력해주세요.")
    private String answer;


    public MultipleChoiceQuestion makeMultipleChoiceQuestion(Quiz quiz) {

        return MultipleChoiceQuestion.builder()
                .content(content)
                .hint(hint)
                .answer(answer)
                .quiz(quiz)
                .example1(examples.get(0))
                .example2(examples.get(1))
                .example3(examples.get(2))
                .example4(examples.get(3))
                .build();


    }



}
