package com.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.domain.Quiz;
import com.quiz.domain.question.Question;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.request.EssayQuestionCreate;
import com.quiz.request.EssayQuestionUpdate;
import com.quiz.request.MultipleChoiceQuestionCreate;
import com.quiz.request.MultipleChoiceQuestionUpdate;
import com.quiz.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

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
    void 객관식문제등록() throws Exception {

        Quiz quiz = Quiz.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        quizRepository.save(quiz);

        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");

        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate("질문입니다.", "힌트없음", examples, "질문3");

        String json = objectMapper.writeValueAsString(multiple);

        mockMvc.perform(post("/question/multiple/{quizId}", quiz.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());


    }


    @Test
    @DisplayName("객관식 수정")
    void 객관식문제수정() throws Exception {

        Quiz quiz = Quiz.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        quizRepository.save(quiz);

        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");
        List<String> change = List.of("수정1", "수정2", "수정3", "수정4");

        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate("질문입니다.", "힌트없음", examples, "질문3");

        questionService.addMultiple(quiz.getId(), multiple);

        MultipleChoiceQuestionUpdate update = new MultipleChoiceQuestionUpdate("수정입니다.", "힌트수정", "수정3", change);

        String json = objectMapper.writeValueAsString(update);
        List<Question> list = questionRepository.findAll();
        Question question = list.get(0);

        mockMvc.perform(patch("/question/multiple/{questionId}", question.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());



    }





    @Test
    @DisplayName("주관식 등록")
    void 주관식문제등록() throws Exception {

        Quiz quiz = Quiz.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .questionCount(0)
                .build();

        quizRepository.save(quiz);

        EssayQuestionCreate essay = new EssayQuestionCreate("질문2입니다.", "힌트없음", "주관식");

        String json = objectMapper.writeValueAsString(essay);

        System.out.println(json);
        mockMvc.perform(post("/question/essay/{quizId}", quiz.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());



    }


    @Test
    @DisplayName("주관식 수정")
    void 주관식문제수정() throws Exception {

        Quiz quiz = Quiz.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .questionCount(0)
                .build();

        quizRepository.save(quiz);

        EssayQuestionCreate essay = new EssayQuestionCreate("질문2입니다.", "힌트없음", "주관식");

        questionService.addEssay(quiz.getId(), essay);

        EssayQuestionUpdate update = new EssayQuestionUpdate("수정입니다.", "힌트수정", "수정이");

        String json = objectMapper.writeValueAsString(update);
        List<Question> list = questionRepository.findAll();
        Question question = list.get(0);

        mockMvc.perform(patch("/question/essay/{questionId}", question.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());


    }


}