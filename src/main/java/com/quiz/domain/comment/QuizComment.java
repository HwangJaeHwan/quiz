package com.quiz.domain.comment;


import com.quiz.domain.Quiz;
import com.quiz.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizComment extends Comment{

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;


    @Builder
    public QuizComment(String content, User user, Quiz quiz) {
        super(content, user);
        this.quiz = quiz;
    }


}
