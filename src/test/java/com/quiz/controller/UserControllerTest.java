package com.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.repository.CommentRepository;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.UserCreate;
import com.quiz.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
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

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    QuizRepository quizRepository;

    @AfterEach
    void clean(){
        questionRepository.deleteAll();
        commentRepository.deleteAll();
        quizRepository.deleteAll();
        userRepository.deleteAll();

    }


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


    @Test
    @DisplayName("로그인")
    void 로그인() throws Exception {

        UserCreate userCreate = new UserCreate("testId", "password", "password", "nickname", "email");

        userService.save(userCreate);

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("loginId", "testId");
        info.add("password", "password");


        mockMvc.perform(post("/login").params(info))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated());






    }

    @Test
    @DisplayName("로그인 실패")
    void 로그인_실패() throws Exception {

        UserCreate userCreate = new UserCreate("testId", "password", "password", "nickname", "email");

        userService.save(userCreate);


        mockMvc.perform(formLogin().user("testId").password("password2"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("아이디 또는 비밀번호가 맞지 않습니다."))
                .andExpect(unauthenticated());






    }

}