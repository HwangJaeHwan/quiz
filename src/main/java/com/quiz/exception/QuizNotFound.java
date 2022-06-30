package com.quiz.exception;

public class QuizNotFound extends QuizException{

    private static final String MESSAGE = "해당하는 퀴즈가 존재하지 않습니다.";


    public QuizNotFound() {
        super(MESSAGE);
    }

    public QuizNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}
