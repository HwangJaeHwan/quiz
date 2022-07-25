package com.quiz.response;

import com.quiz.domain.comment.QuizComment;
import lombok.Getter;


@Getter
public class QuizCommentListResponse {

    private Long id;

    private String content;

    private String nickname;


    public QuizCommentListResponse(QuizComment quizComment) {
        this.id = quizComment.getId();
        this.content = quizComment.getContent();
        this.nickname = quizComment.getUser().getNickname();
    }
}
