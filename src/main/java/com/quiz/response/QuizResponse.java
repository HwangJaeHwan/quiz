package com.quiz.response;

import com.quiz.request.QuestionType;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@ToString
public class QuizResponse {


    private Long id;
    private String title;

    private String content;

    private List<QuestionResponse> questions;

    @Builder
    public QuizResponse(Long id, String title, String content, List<QuestionResponse> questions) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.questions = questions;
    }
}
