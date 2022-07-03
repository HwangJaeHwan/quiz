package com.quiz.service;

import com.quiz.domain.Quiz;
import com.quiz.domain.question.Question;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.request.*;
import com.quiz.response.QuestionResponse;
import com.quiz.response.QuizListResponse;
import com.quiz.response.QuizResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class QuizServiceTest {

    @Autowired
    QuizService quizService;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionService questionService;


    @Test
    @DisplayName("quiz 등록")
    void test1() {



        QuizCreate quizCreate = new QuizCreate("제목입니다.", "내용입니다.");

        quizService.write(quizCreate);


        Quiz quiz = quizRepository.findById(1L).get();
        assertThat(quiz.getTitle()).isEqualTo("제목입니다.");
        assertThat(quiz.getContent()).isEqualTo("내용입니다.");
        assertThat(1L).isEqualTo(quizRepository.count());

    }


    @Test
    @DisplayName("quiz 조회")
    void 퀴즈조회() {

        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");


        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate(1, "질문입니다.", "힌트없음", examples, "질문3");
        EssayQuestionCreate essay = new EssayQuestionCreate(2, "질문2입니다.", "힌트없음", "주관식");


        QuizCreate quizCreate = new QuizCreate("제목입니다.", "내용입니다.");

        quizService.write(quizCreate);

        questionService.addMultiple(1L, multiple);
        questionService.addEssay(1L, essay);


        QuizResponse quiz = quizService.quiz(1L);

        assertThat(quiz.getTitle()).isEqualTo("제목입니다.");
        assertThat(quiz.getContent()).isEqualTo("내용입니다.");
        assertThat(quiz.getQuestions().size()).isEqualTo(2L);


    }

    @Test
    @DisplayName("퀴즈 리스트 조회")
    void 퀴즈리스트(){

        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");


        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate(1, "질문입니다.", "힌트없음", examples, "질문3");
        EssayQuestionCreate essay = new EssayQuestionCreate(2, "질문2입니다.", "힌트없음", "주관식");


        QuizCreate quizCreate = new QuizCreate("제목입니다.", "내용입니다.");
        QuizCreate quizCreate2 = new QuizCreate("제목입니다2.", "내용입니다2.");

        quizService.write(quizCreate);
        quizService.write(quizCreate2);

        questionService.addMultiple(1L, multiple);
        questionService.addEssay(1L, essay);

        questionService.addMultiple(2L, multiple);
        questionService.addEssay(2L, essay);


        List<QuizListResponse> list = quizService.getList();

        assertThat(list.size()).isEqualTo(2L);
        assertThat(list.get(0).getTitle()).isEqualTo("제목입니다.");
        assertThat(list.get(1).getTitle()).isEqualTo("제목입니다2.");



    }




}