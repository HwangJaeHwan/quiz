package com.quiz.response;

import com.quiz.domain.Quiz;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuizListResponse {

    private Long id;

    private String title;

    private String nickname;

    private Integer questionCount;

    private LocalDateTime createdTime;

    public QuizListResponse(Quiz quiz) {
        this.id = quiz.getId();
        this.title = quiz.getTitle();
        this.nickname = quiz.getUser().getNickname();
        this.questionCount = quiz.getQuestionCount();
        this.createdTime = quiz.getCreatedTime();
    }
}
