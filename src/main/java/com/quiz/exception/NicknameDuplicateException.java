package com.quiz.exception;

public class NicknameDuplicateException extends QuizException{

    private static final String MESSAGE = "닉네임이 중복됩니다.";

    public NicknameDuplicateException() {
        super(MESSAGE);
    }

    public NicknameDuplicateException(Throwable cause) {
        super(MESSAGE, cause);
    }


    @Override
    public int getStatusCode() {
        return 400;
    }
}
