package com.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.request.*;
import com.quiz.service.QuestionService;
import com.quiz.service.QuizService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
class QuizControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    QuizService quizService;

    @Autowired
    QuestionService questionService;

    @Test
    @DisplayName("/quiz test")
    void 등록테스트() throws Exception {



        QuizCreate quizCreate = new QuizCreate("제목입니다", "내용입니다.");

        String json = objectMapper.writeValueAsString(quizCreate);


        mockMvc.perform(post("/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());





    }


    @Test
    @DisplayName("/quiz validation 테스트")
    void 검증테스트() throws Exception {


        QuizCreate quizCreate = new QuizCreate("", "내용입니다.");

        String json = objectMapper.writeValueAsString(quizCreate);


        mockMvc.perform(post("/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목을 입력해주세요."))
                .andDo(print());

    }


    @Test
    @DisplayName("/quiz/{quizId} test(단건 조회)")
    void 단건조회() throws Exception {


        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");


        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate(1, "질문입니다.", "힌트없음", examples, "질문3");
        EssayQuestionCreate essay = new EssayQuestionCreate(2, "질문2입니다.", "힌트없음", "주관식");


        QuizCreate quizCreate = new QuizCreate("제목입니다.", "내용입니다.");

        quizService.write(quizCreate);

        questionService.addEssay(1L, essay);
        questionService.addMultiple(1L, multiple);




        mockMvc.perform(get("/quiz/{quizId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목입니다."))
                .andExpect(jsonPath("$.content").value("내용입니다."))
                .andExpect(jsonPath("$.questions.length()").value(2))
                .andExpect(jsonPath("$.questions[0].content").value("질문입니다."))
                .andExpect(jsonPath("$.questions[1].content").value("질문2입니다."))
                .andDo(print());




    }

    @Test
    @DisplayName("/quiz test(리스트 조회)")
    void 리스트조회() throws Exception {


        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");


        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate(1, "질문입니다.", "힌트없음", examples, "질문3");
        EssayQuestionCreate essay = new EssayQuestionCreate(2, "질문2입니다.", "힌트없음", "주관식");


        QuizCreate quizCreate = new QuizCreate("제목입니다.", "내용입니다.");
        QuizCreate quizCreate2 = new QuizCreate("제목2입니다.", "내용2입니다.");


        quizService.write(quizCreate);
        quizService.write(quizCreate2);

        questionService.addEssay(1L, essay);
        questionService.addMultiple(1L, multiple);

        questionService.addEssay(2L, essay);
        questionService.addMultiple(2L, multiple);


        mockMvc.perform(get("/quiz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("제목입니다."))
                .andExpect(jsonPath("$[0].questionCount").value(2))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("제목2입니다."))
                .andExpect(jsonPath("$[1].questionCount").value(2))
                .andDo(print());




    }

}