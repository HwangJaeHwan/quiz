package com.quiz.response;

import com.quiz.domain.question.EssayQuestion;
import com.quiz.domain.question.MultipleChoiceQuestion;
import com.quiz.domain.question.Question;
import com.quiz.request.QuestionType;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class QuestionResponse {


    private Integer number;

    private String content;

    private QuestionType questionType;

    private String hint;

    private List<String> examples;

    private String answer;


    public QuestionResponse(Question question) {

        if (question instanceof EssayQuestion) {

            EssayQuestion essayQuestion = (EssayQuestion) question;

            this.number = essayQuestion.getNumber();
            this.content = essayQuestion.getContent();
            this.questionType = QuestionType.ESSAY;
            this.hint = essayQuestion.getHint();
            this.answer = essayQuestion.getAnswer();


        } else {

            MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;

            this.number = multipleChoiceQuestion.getNumber();
            this.content = multipleChoiceQuestion.getContent();
            this.questionType = QuestionType.MULTIPLE_CHOICE;
            this.hint = multipleChoiceQuestion.getHint();
            this.answer = multipleChoiceQuestion.getAnswer();
            this.examples = multipleChoiceQuestion.getExamples();
        }



    }

}
