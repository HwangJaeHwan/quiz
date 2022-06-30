package com.quiz.response;

import com.quiz.request.QuestionType;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class QuizResponse {

    private String title;

    private String content;

    private List<QuestionResponse> questions;

    @Builder
    public QuizResponse(String title, String content, List<QuestionResponse> questions) {
        this.title = title;
        this.content = content;
        this.questions = questions;
    }
}
