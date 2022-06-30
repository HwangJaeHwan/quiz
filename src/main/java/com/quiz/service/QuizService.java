package com.quiz.service;

import com.quiz.domain.Quiz;
import com.quiz.domain.question.EssayQuestion;
import com.quiz.domain.question.MultipleChoiceQuestion;
import com.quiz.domain.question.Question;
import com.quiz.exception.QuizNotFound;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.request.*;
import com.quiz.response.QuestionResponse;
import com.quiz.response.QuizResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    public void write(QuizCreate request) {

        log.info("request.getTitle ={}", request.getTitle());

        if (request.getEssayQuestions() == null && request.getMultipleChoiceQuestions() == null) {
            throw new IllegalStateException("이상이상");
        }

        Quiz quiz = Quiz.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        quizRepository.save(quiz);

        log.info("quiz id = {}", quiz.getId());


        if (request.getMultipleChoiceQuestions() != null) {


            List<MultipleChoiceQuestion> questions = new ArrayList<>();

            for (MultipleChoiceQuestionCreate multipleChoiceQuestion : request.getMultipleChoiceQuestions()) {
                log.info("multiple insert");
                questions.add(multipleChoiceQuestion.makeMultipleChoiceQuestion(quiz));

            }

            questionRepository.saveAll(questions);

        }

        if (request.getEssayQuestions() != null) {


            List<EssayQuestion> questions = new ArrayList<>();


            for (EssayQuestionCreate essayQuestion : request.getEssayQuestions()) {
                log.info("essay insert");
                questions.add(essayQuestion.makeEssayQuestion(quiz));
            }

            questionRepository.saveAll(questions);

        }

    }


    public QuizResponse quiz(Long quizId){

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(QuizNotFound::new);




        List<QuestionResponse> questions = questionRepository.findAllByQuiz(quizId).stream().map(QuestionResponse::new).collect(Collectors.toList());


        return QuizResponse.builder()
                .title(quiz.getTitle())
                .content(quiz.getContent())
                .questions(questions)
                .build();

    }


}
