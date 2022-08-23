package com.quiz.service;

import com.quiz.domain.Quiz;
import com.quiz.domain.User;
import com.quiz.exception.QuizNotFound;
import com.quiz.exception.UserNotFound;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.*;
import com.quiz.response.QuestionResponse;
import com.quiz.response.QuizListInfo;
import com.quiz.response.QuizListResponse;
import com.quiz.response.QuizResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public void write(Long userId, QuizCreate request) {


        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);

        log.info("request.getTitle ={}", request.getTitle());

        Quiz quiz = Quiz.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .questionCount(0)
                .build();

        quizRepository.save(quiz);


    }

    public void edit(Long quizId, QuizEdit quizEdit){
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(QuizNotFound::new);
        quiz.edit(quizEdit);
    }


    public QuizResponse quiz(Long quizId){

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(QuizNotFound::new);


        List<QuestionResponse> questions = questionRepository.findAllByQuizId(quizId).stream().map(QuestionResponse::new).collect(Collectors.toList());


        return QuizResponse.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .content(quiz.getContent())
                .questions(questions)
                .build();

    }


    public QuizListInfo getList(PageDTO pageDTO, SearchDTO searchDTO) {

        Page<Quiz> quizList = quizRepository.quizList(pageDTO, searchDTO);


        return QuizListInfo.builder()
                .totalPage(quizList.getTotalPages())
                .list(quizList.map(QuizListResponse::new).getContent())
                .build();



    }

    public void delete(Long quizId){

        questionRepository.deleteAllByQuizId(quizId);

        quizRepository.deleteById(quizId);

    }
}
