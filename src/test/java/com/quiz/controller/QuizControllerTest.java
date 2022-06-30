package com.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.request.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@Rollback(value = false)
@SpringBootTest
class QuizControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("/quiz test")
    void 등록테스트() throws Exception {


        List<String> examples = new ArrayList<>();

        examples.add("질문1");
        examples.add("질문2");
        examples.add("질문3");
        examples.add("질문4");

        List<MultipleChoiceQuestionCreate> multiple = new ArrayList<>();
        List<EssayQuestionCreate> essay = new ArrayList<>();

        multiple.add(new MultipleChoiceQuestionCreate(1, "질문입니다.", "힌트없음", examples, "질문3"));
        essay.add(new EssayQuestionCreate(2, "질문2입니다.", "힌트없음", "주관식"));


        QuizCreate quizCreate = new QuizCreate("제목입니다", "내용입니다.", essay, multiple);

        String json = objectMapper.writeValueAsString(quizCreate);
        System.out.println(json);


        mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());





    }


    @Test
    @DisplayName("/quiz validation 테스트")
    void 검증테스트() throws Exception {


        List<String> examples = new ArrayList<>();

        examples.add("질문1");
        examples.add("질문2");
        examples.add("질문3");
        examples.add("질문4");
        examples.add("질문5");

        List<MultipleChoiceQuestionCreate> multiple = new ArrayList<>();
        List<EssayQuestionCreate> essay = new ArrayList<>();

        multiple.add(new MultipleChoiceQuestionCreate(1, "", "힌트없음", examples, "질문3"));
        essay.add(new EssayQuestionCreate(2, "내용입니다.", "힌트없음", ""));


        QuizCreate quizCreate = new QuizCreate("제목입니다", "내용입니다.", essay, multiple);

        String json = objectMapper.writeValueAsString(quizCreate);
        System.out.println(json);


        mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andDo(print());





    }

}