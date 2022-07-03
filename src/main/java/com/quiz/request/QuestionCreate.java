package com.quiz.request;

import com.quiz.domain.Quiz;
import com.quiz.domain.question.EssayQuestion;
import com.quiz.domain.question.MultipleChoiceQuestion;
import com.quiz.domain.question.Question;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreate {

    @NotNull
    private Integer number;

    @NotBlank
    private String content;

    @NotNull
    private QuestionType questionType;

    private String hint;

    private List<String> examples;

    private String answer;



}
