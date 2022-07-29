package com.quiz.exception;

public class NotCommentOwnerException extends QuizException{


    private static final String MESSAGE = "댓글의 작성자가 아닙니다.";



    public NotCommentOwnerException() {
        super(MESSAGE);
    }

    public NotCommentOwnerException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 403;
    }

}
