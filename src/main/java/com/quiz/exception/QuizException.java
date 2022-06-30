package com.quiz.exception;

public abstract class QuizException extends RuntimeException {


    public QuizException(String message) {
        super(message);
    }

    public QuizException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();


}
