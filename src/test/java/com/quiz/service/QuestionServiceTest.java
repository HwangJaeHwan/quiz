package com.quiz.service;

import com.quiz.domain.Quiz;
import com.quiz.domain.question.EssayQuestion;
import com.quiz.domain.question.MultipleChoiceQuestion;
import com.quiz.domain.question.Question;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.request.EssayQuestionCreate;
import com.quiz.request.MultipleChoiceQuestionCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionService questionService;

    @BeforeEach
    void clean(){
        questionRepository.deleteAll();
        quizRepository.deleteAll();

    }




    @Test
    @DisplayName("객관식 등록")
    void 객관식(){

        Quiz quiz = Quiz.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .questionCount(0)
                .build();

        quizRepository.save(quiz);

        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");

        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate("질문입니다.", "힌트없음", examples, "질문3");

        questionService.addMultiple(quiz.getId(), multiple);

        List<Question> list = questionRepository.findAll();

        assertThat(list.size()).isEqualTo(1L);
        assertThat(list.get(0)).isInstanceOf(MultipleChoiceQuestion.class);


    }


    @Test
    @DisplayName("주관식 등록")
    void 주관식(){

        Quiz quiz = Quiz.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .questionCount(0)
                .build();

        quizRepository.save(quiz);

        EssayQuestionCreate essay = new EssayQuestionCreate("질문2입니다.", "힌트없음", "주관식");

        questionService.addEssay(quiz.getId(), essay);

        List<Question> list = questionRepository.findAll();

        assertThat(list.size()).isEqualTo(1L);
        assertThat(list.get(0)).isInstanceOf(EssayQuestion.class);


    }


}