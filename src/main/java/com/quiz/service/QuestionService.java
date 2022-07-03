package com.quiz.service;


import com.quiz.domain.Quiz;
import com.quiz.domain.question.EssayQuestion;
import com.quiz.domain.question.MultipleChoiceQuestion;
import com.quiz.exception.QuizNotFound;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.request.EssayQuestionCreate;
import com.quiz.request.MultipleChoiceQuestionCreate;
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

    public void addEssay(Long postId, EssayQuestionCreate request) {

        Quiz quiz = quizRepository.findById(postId).orElseThrow(QuizNotFound::new);

        quiz.addQuestionCount();

        EssayQuestion essayQuestion = request.makeEssayQuestion(quiz);

        questionRepository.save(essayQuestion);


    }


    public void addMultiple(Long postId, MultipleChoiceQuestionCreate request){

        Quiz quiz = quizRepository.findById(postId).orElseThrow(QuizNotFound::new);

        quiz.addQuestionCount();

        MultipleChoiceQuestion multipleChoiceQuestion = request.makeMultipleChoiceQuestion(quiz);
        multipleChoiceQuestion.addExamples(request.getExamples());

        questionRepository.save(multipleChoiceQuestion);

    }


}
