package com.quiz.response;

import com.quiz.domain.Quiz;
import com.quiz.domain.question.MultipleChoiceQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
public class MultipleChoiceQuestionUpdate extends QuestionUpdate {


    @Size(min = 2, max = 4,message = "보기는 2~4개까지 생성가능합니다.")
    private List<String> examples;


    public MultipleChoiceQuestionUpdate(String content, String hint, String answer, List<String> examples) {
        super(content, hint, answer);
        this.examples = examples;
    }
}
