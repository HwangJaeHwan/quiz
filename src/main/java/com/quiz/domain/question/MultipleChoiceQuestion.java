package com.quiz.domain.question;

import com.quiz.domain.Quiz;
import com.quiz.response.MultipleChoiceQuestionUpdate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoiceQuestion extends Question{


    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    List<Example> examples = new ArrayList<>();


    @Builder
    public MultipleChoiceQuestion(Quiz quiz, String content, String hint,String answer) {
        super(quiz, content, hint, answer);

    }


    public void addExamples(List<String> examples){

        for (String example : examples) {

            this.examples.add(Example.builder()
                    .content(example)
                    .question(this)
                    .build());

        }

    }

    public void deleteExamples(){
        this.examples.clear();
    }



}
