package com.quiz.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class QuizListInfo {

    private int totalPage;

    private List<QuizListResponse> quizInfo;

    @Builder
    public QuizListInfo(int totalPage, List<QuizListResponse> quizInfo) {
        this.totalPage = totalPage;
        this.quizInfo = quizInfo;
    }


}
