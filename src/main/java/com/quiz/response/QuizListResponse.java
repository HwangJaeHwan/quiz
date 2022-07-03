package com.quiz.response;

import com.quiz.domain.Quiz;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuizListResponse {

    private Long id;

    private String title;

    private Integer questionCount;

    private LocalDateTime createdTime;

    public QuizListResponse(Quiz quiz) {
        this.id = quiz.getId();
        this.title = quiz.getTitle();
        this.questionCount = quiz.getQuestionCount();
        this.createdTime = quiz.getCreatedTime();
    }
}
