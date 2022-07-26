package com.quiz.exception;

public class CommentNotFound extends QuizException{

    private static final String MESSAGE = "해당하는 댓글이 존재하지 않습니다.";


    public CommentNotFound() {
        super(MESSAGE);
    }

    public CommentNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}
