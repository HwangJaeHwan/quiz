package com.quiz.domain.question;

import com.quiz.domain.Quiz;
import com.quiz.request.MultipleChoiceQuestionEdit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoiceQuestion extends Question{

    private String example1;
    private String example2;
    private String example3;
    private String example4;

    @Builder
    public MultipleChoiceQuestion(Quiz quiz, String content, String hint,String answer,
                                  String example1,String example2,String example3,String example4) {
        super(quiz, content, hint, answer);
        this.example1 = example1;
        this.example2 = example2;
        this.example3 = example3;
        this.example4 = example4;

    }

    public void multipleEdit(MultipleChoiceQuestionEdit edit) {

        editAnswer(edit.getAnswer());
        editContent(edit.getContent());
        editHInt(edit.getHint());

        this.example1 = edit.getExamples().get(0);
        this.example2 = edit.getExamples().get(1);
        this.example3 = edit.getExamples().get(2);
        this.example4 = edit.getExamples().get(3);

    }


}
