package com.quiz.domain.question;

import com.quiz.domain.Quiz;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoiceQuestion extends Question{


    private String example1;

    private String example2;

    private String example3;

    private String example4;



    @Builder
    public MultipleChoiceQuestion(Quiz quiz, int number, String content, String hint, List<String> examples,String answer) {
        super(quiz, number, content, hint, answer);


        example1 = examples.get(0);
        example2 = examples.get(1);
        example3 = examples.get(2);
        example4 = examples.get(3);

    }


    public List<String> getExamples(){
        return List.of(example1, example2, example3, example4);
    }

}
