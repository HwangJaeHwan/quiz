package com.quiz.response;

import com.quiz.domain.question.EssayQuestion;
import com.quiz.domain.question.Example;
import com.quiz.domain.question.MultipleChoiceQuestion;
import com.quiz.domain.question.Question;
import com.quiz.request.QuestionType;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class QuestionResponse {


    private Long id;
    private Integer number;

    private String content;

    private QuestionType questionType;

    private String hint;

    private List<String> examples = new ArrayList<>();

    private String answer;


    public QuestionResponse(Question question) {

        this.id = question.getId();
        this.number = question.getNumber();
        this.content = question.getContent();
        this.hint = question.getHint();
        this.answer = question.getAnswer();
        this.questionType = QuestionType.ESSAY;



        if (question instanceof MultipleChoiceQuestion) {

            MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;

            for (Example example : multipleChoiceQuestion.getExamples()) {
                examples.add(example.getContent());
            }

            this.questionType = QuestionType.MULTIPLE_CHOICE;

        }



    }

}
