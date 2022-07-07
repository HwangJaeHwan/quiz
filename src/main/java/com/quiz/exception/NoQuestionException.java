package com.quiz.exception;

public class NoQuestionException extends QuizException {

    private static String MESSAGE = "문제가 존재하지 않습니다.";



    public NoQuestionException() {
        super(MESSAGE);
    }

    public NoQuestionException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}
