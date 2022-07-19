package com.quiz.exception;

public class PasswordNotEqualException extends QuizException{

    private static final String MESSAGE = "비밀번호와 비밀번호 체크가 다릅니다.";

    public PasswordNotEqualException() {
        super(MESSAGE);
    }

    public PasswordNotEqualException(Throwable cause) {
        super(MESSAGE, cause);
    }


    @Override
    public int getStatusCode() {
        return 400;
    }
}
