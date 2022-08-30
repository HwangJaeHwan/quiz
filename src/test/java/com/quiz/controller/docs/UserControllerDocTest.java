package com.quiz.controller.docs;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.repository.CommentRepository;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.UserCreate;
import com.quiz.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.quiz.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class UserControllerDocTest {


    @Autowired
    private MockMvc mockMvc;

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

        UserCreate userCreate = new UserCreate("testId", "password", "password", "nickname", "test@test.com");

        String json = objectMapper.writeValueAsString(userCreate);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isOk())
                .andDo(document("user-register",
                        requestFields(
                                fieldWithPath("loginId").description("아이디"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("passwordCheck").description("비밀번호 확인"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("email").description("이메일")
                                )
                ))
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


        mockMvc.perform(RestDocumentationRequestBuilders.post("/login").params(info))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andDo(document("user-login",
                        requestParameters(parameterWithName("loginId").description("로그인 아이디"),
                                parameterWithName("password").description("비밀번호")
                        )
                ))
                .andExpect(authenticated());


    }



    @Test
    @DisplayName("네이버 로그인")
    void 네이버로그인() throws Exception {


        mockMvc.perform(RestDocumentationRequestBuilders.get("/oauth2/authorization/naver"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andDo(document("user-naver"));


    }

    @Test
    @DisplayName("카카오 로그인")
    void 카카오로그인() throws Exception {


        mockMvc.perform(RestDocumentationRequestBuilders.get("/oauth2/authorization/kakao"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andDo(document("user-kakao"));


    }

    @Test
    @DisplayName("구글 로그인")
    void 구글로그인() throws Exception {


        mockMvc.perform(RestDocumentationRequestBuilders.get("/oauth2/authorization/google"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andDo(document("user-google"));


    }




}
