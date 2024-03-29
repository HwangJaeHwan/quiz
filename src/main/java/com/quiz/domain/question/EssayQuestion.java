package com.quiz.domain.question;

import com.quiz.domain.Quiz;
import com.quiz.request.EssayQuestionEdit;
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
    public EssayQuestion(Quiz quiz, String content, String hint, String answer) {
        super(quiz, content, hint, answer);
    }


    public void essayEdit(EssayQuestionEdit edit) {
        editContent(edit.getContent());
        editHInt(edit.getHint());
        editAnswer(edit.getAnswer());
    }

}
