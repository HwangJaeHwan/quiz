package com.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.request.UserCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("회원가입")
    void 회원가입() throws Exception {

        UserCreate userCreate = new UserCreate("testId", "password", "password", "nickname", "email");

        String json = objectMapper.writeValueAsString(userCreate);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("회원가입 비밀번호 다름")
    void 회원가입_비밀번호다름() throws Exception {

        UserCreate userCreate = new UserCreate("testId", "password", "password2", "nickname", "email");

        String json = objectMapper.writeValueAsString(userCreate);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("비밀번호와 비밀번호 체크가 다릅니다."))
                .andDo(print());

    }

    @Test
    @DisplayName("회원가입 정보 누락")
    void 회원가입_회원정보_누락() throws Exception {

        UserCreate userCreate = new UserCreate("", "password", "password", "nickname", "email");

        String json = objectMapper.writeValueAsString(userCreate);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.loginId").value("아이디를 입력해주세요"))
                .andDo(print());

    }

}