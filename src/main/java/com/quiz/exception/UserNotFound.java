package com.quiz.exception;

public class UserNotFound extends QuizException{

    private static String MESSAGE = "해당하는 유저를 찾을수 없습니다.";

    public UserNotFound() {
        super(MESSAGE);
    }

    public UserNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }


    @Override
    public int getStatusCode() {
        return 404;
    }



}
