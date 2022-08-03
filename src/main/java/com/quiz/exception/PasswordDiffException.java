package com.quiz.exception;

public class PasswordDiffException extends QuizException{

    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public PasswordDiffException() {
        super(MESSAGE);
    }

    public PasswordDiffException(Throwable cause) {
        super(MESSAGE, cause);
    }


    @Override
    public int getStatusCode() {
        return 400;
    }
}
