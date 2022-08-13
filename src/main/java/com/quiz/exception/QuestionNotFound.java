package com.quiz.exception;

public class QuestionNotFound extends QuizException{

    private static final String MESSAGE = "해당하는 질문이 존재하지 않습니다.";


    public QuestionNotFound() {
        super(MESSAGE);
    }

    public QuestionNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}
