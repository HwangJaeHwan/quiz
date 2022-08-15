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

    private String content;

    private QuestionType questionType;

    private String hint;

    private List<String> examples = new ArrayList<>();

    private String answer;


    public QuestionResponse(Question question) {

        this.id = question.getId();
        this.content = question.getContent();
        this.hint = question.getHint();
        this.answer = question.getAnswer();
        this.questionType = QuestionType.ESSAY;



        if (question instanceof MultipleChoiceQuestion) {

            MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;

            examples.add(multipleChoiceQuestion.getExample1());
            examples.add(multipleChoiceQuestion.getExample2());
            examples.add(multipleChoiceQuestion.getExample3());
            examples.add(multipleChoiceQuestion.getExample4());



            this.questionType = QuestionType.MULTIPLE_CHOICE;

        }



    }

}
