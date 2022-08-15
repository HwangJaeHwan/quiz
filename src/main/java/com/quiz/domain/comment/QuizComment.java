package com.quiz.domain.comment;


import com.quiz.domain.Quiz;
import com.quiz.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizComment extends Comment{

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;


    @Builder
    public QuizComment(String content, User user, Quiz quiz) {
        super(content, user);
        this.quiz = quiz;
    }


}
