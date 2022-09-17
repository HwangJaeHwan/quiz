package com.quiz.controller.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.auth.UserInfo;
import com.quiz.controller.security.WithAuthUser;
import com.quiz.domain.Quiz;
import com.quiz.domain.User;
import com.quiz.domain.comment.QuizComment;
import com.quiz.repository.CommentRepository;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.CommentCreate;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.quiz.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureMockMvc
class CommentControllerDocTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CommentRepository commentRepository;

    @AfterEach
    void clean(){
        questionRepository.deleteAll();
        commentRepository.deleteAll();
        quizRepository.deleteAll();
        userRepository.deleteAll();

    }




    @Test
    @WithAuthUser(username = "quiz")
    @DisplayName("퀴즈 댓글 리스트")
    void quizCommentList() throws Exception {

        List<QuizComment> list = new ArrayList<>();


        User user = User.builder()
                .username("test")
                .password("password")
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();

        userRepository.save(user);

        Quiz quiz = Quiz.builder()
                .title("quizTest")
                .content("quizTestContent")
                .user(user)
                .questionCount(0)
                .build();

        quizRepository.save(quiz);

        IntStream.rangeClosed(1,5).forEach(
                i->{
                    list.add(QuizComment.builder()
                            .content("content" + i)
                            .user(user)
                            .quiz(quiz)
                            .build());

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
        );

        commentRepository.saveAll(list);


        mockMvc.perform(RestDocumentationRequestBuilders.get("/comment/quiz/{quizId}", quiz.getId()))
                .andExpect(status().isOk())
                .andDo(document("quiz-comment-list",
                        pathParameters(
                                parameterWithName("quizId").description("퀴즈 ID")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("댓글 ID"),
                                fieldWithPath("[].content").description("댓글 내용"),
                                fieldWithPath("[].nickname").description("작성자 닉네임")

                        )
                ))
                .andDo(print());


    }

    @Test
    @WithAuthUser(username = "quiz")
    @DisplayName("퀴즈 댓글 쓰기")
    void writeQuizComment() throws Exception {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        log.info("user name = {}",userInfo.getUser().getUsername());


        Quiz quiz = Quiz.builder()
                .title("quizTest")
                .content("quizTestContent")
                .user(userInfo.getUser())
                .questionCount(0)
                .build();

        quizRepository.save(quiz);

        CommentCreate commentCreate = new CommentCreate("테스트");


        String json = objectMapper.writeValueAsString(commentCreate);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/comment/quiz/{quizId}", quiz.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(document("quiz-comment-create",
                        pathParameters(
                                parameterWithName("quizId").description("퀴즈 ID")
                        ),
                        requestFields(
                                fieldWithPath("content").description("댓글 내용")
                        )
                        ))
                .andExpect(status().isOk())
                .andDo(print());



    }






}