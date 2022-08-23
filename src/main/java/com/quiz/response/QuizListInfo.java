package com.quiz.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class QuizListInfo {

    private int totalPage;

    private List<QuizListResponse> list;

    @Builder
    public QuizListInfo(int totalPage, List<QuizListResponse> list) {
        this.totalPage = totalPage;
        this.list = list;
    }


}
