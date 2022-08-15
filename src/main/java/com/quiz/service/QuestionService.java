package com.quiz.service;


import com.quiz.domain.Quiz;
import com.quiz.domain.question.EssayQuestion;
import com.quiz.domain.question.MultipleChoiceQuestion;
import com.quiz.exception.QuestionNotFound;
import com.quiz.exception.QuizNotFound;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.request.EssayQuestionCreate;
import com.quiz.request.MultipleChoiceQuestionCreate;
import com.quiz.response.EssayQuestionUpdate;
import com.quiz.response.MultipleChoiceQuestionUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public void addEssay(Long quizId, EssayQuestionCreate request) {

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(QuizNotFound::new);

        quiz.addQuestionCount();

        EssayQuestion essayQuestion = request.makeEssayQuestion(quiz);

        questionRepository.save(essayQuestion);


    }

    public void updateEssay(Long questionId, EssayQuestionUpdate essayQuestionUpdate){

        EssayQuestion essayQuestion = questionRepository.findEssayQuestionById(questionId).orElseThrow(QuestionNotFound::new);

        essayQuestion.essayUpdate(essayQuestionUpdate);


    }


    public void addMultiple(Long quizId, MultipleChoiceQuestionCreate request){

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(QuizNotFound::new);

        quiz.addQuestionCount();

        MultipleChoiceQuestion multipleChoiceQuestion = request.makeMultipleChoiceQuestion(quiz);

        questionRepository.save(multipleChoiceQuestion);

    }

    public void updateMultiple(Long questionId, MultipleChoiceQuestionUpdate multipleChoiceQuestionUpdate) {
        MultipleChoiceQuestion multipleChoiceQuestion = questionRepository.findMultipleQuestionById(questionId).orElseThrow(QuestionNotFound::new);

        multipleChoiceQuestion.multipleUpdate(multipleChoiceQuestionUpdate);


    }


}
