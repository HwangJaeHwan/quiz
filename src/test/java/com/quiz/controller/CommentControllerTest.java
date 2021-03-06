package com.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.domain.Quiz;
import com.quiz.domain.User;
import com.quiz.domain.comment.QuizComment;
import com.quiz.repository.CommentRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    CommentRepository commentRepository;



    @Test
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
                .questionCount(0)
                .build();

        quizRepository.save(quiz);

        IntStream.rangeClosed(1,100).forEach(
                i->{
                    list.add(QuizComment.builder()
                            .content("content" + i)
                            .user(user)
                            .quiz(quiz)
                            .build());

                }
        );

        commentRepository.saveAll(list);


        mockMvc.perform(get("/comment/quiz/{quizId}", quiz.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(100L))
                .andExpect(jsonPath("$[0].content").value("content100"))
                .andExpect(jsonPath("$[0].nickname").value("nickname"))

                .andDo(print());


    }



}