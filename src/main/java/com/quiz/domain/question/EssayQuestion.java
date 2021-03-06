package com.quiz.domain.question;

import com.quiz.domain.Quiz;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EssayQuestion extends Question {

    @Builder
    public EssayQuestion(Quiz quiz, int number, String content, String hint, String answer) {
        super(quiz, number, content, hint, answer);
    }

}
