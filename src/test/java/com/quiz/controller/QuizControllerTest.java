package com.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.controller.security.WithAuthUser;
import com.quiz.domain.Quiz;
import com.quiz.domain.User;
import com.quiz.domain.comment.QuizComment;
import com.quiz.repository.CommentRepository;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.*;
import com.quiz.service.QuestionService;
import com.quiz.service.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Slf4j
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
    @WithAuthUser(username = "test")
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
    @WithAuthUser(username = "test")
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
    @WithAuthUser(username = "test")
    @DisplayName("/quiz/{quizId} test(단건 조회)")
    void 단건조회() throws Exception {


        User user = User.builder()
                .username("test")
                .password("password")
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();

        userRepository.save(user);


        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");


        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate("객관식 질문입니다.", "힌트없음", examples, "질문3");
        EssayQuestionCreate essay = new EssayQuestionCreate("주관식 질문입니다.", "힌트없음", "주관식");


        QuizCreate quizCreate = new QuizCreate("제목입니다.", "내용입니다.");


        quizService.write(user.getId(), quizCreate);

        List<Quiz> quizList = quizRepository.findAll();


        questionService.addEssay(quizList.get(0).getId(), essay);
        questionService.addMultiple(quizList.get(0).getId(), multiple);




        mockMvc.perform(get("/quiz/{quizId}", quizList.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목입니다."))
                .andExpect(jsonPath("$.content").value("내용입니다."))
                .andExpect(jsonPath("$.questions.length()").value(2))
                .andExpect(jsonPath("$.questions[0].content").value("주관식 질문입니다."))
                .andExpect(jsonPath("$.questions[1].content").value("객관식 질문입니다."))
                .andDo(print());




    }

    @Test
    @WithAuthUser(username = "test")
    @DisplayName("리스트 조회")
    void 리스트조회() throws Exception {

        User user = User.builder()
                .username("test")
                .password("password")
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();

        userRepository.save(user);


        IntStream.rangeClosed(1,100).forEach(
                i->{
                    quizRepository.save(Quiz.builder()
                            .title("제목" + i)
                            .user(user)
                            .content("내용" + i)
                            .questionCount(0)
                            .build());
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
        );


        mockMvc.perform(get("/quiz?page=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPage").value(5L))
                .andExpect(jsonPath("$.list.length()").value(20L))
                .andExpect(jsonPath("$.list[0].title").value("제목100"))
                .andExpect(jsonPath("$.list[1].title").value("제목99"))
                .andExpect(jsonPath("$.list[2].title").value("제목98"))
                .andExpect(jsonPath("$.list[3].title").value("제목97"))
                .andExpect(jsonPath("$.list[4].title").value("제목96"))
                .andExpect(jsonPath("$.list[5].title").value("제목95"))
                .andExpect(jsonPath("$.list[6].title").value("제목94"))
                .andExpect(jsonPath("$.list[7].title").value("제목93"))
                .andExpect(jsonPath("$.list[8].title").value("제목92"))
                .andExpect(jsonPath("$.list[9].title").value("제목91"))
                .andExpect(jsonPath("$.list[10].title").value("제목90"))

                .andDo(print());




    }


    @Test
    @WithAuthUser(username = "test")
    @DisplayName("리스트 조회 페이지 0")
    void 리스트조회_페이지0() throws Exception {

        User user = User.builder()
                .username("test")
                .password("password")
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();

        userRepository.save(user);


        IntStream.rangeClosed(1,100).forEach(
                i->{
                    quizRepository.save(Quiz.builder()
                            .title("제목" + i)
                            .user(user)
                            .content("내용" + i)
                            .questionCount(0)
                            .build());

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
        );


        mockMvc.perform(get("/quiz?page=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPage").value(5L))
                .andExpect(jsonPath("$.list.length()").value(20L))
                .andExpect(jsonPath("$.list[0].title").value("제목100"))
                .andExpect(jsonPath("$.list[1].title").value("제목99"))
                .andExpect(jsonPath("$.list[2].title").value("제목98"))
                .andExpect(jsonPath("$.list[3].title").value("제목97"))
                .andExpect(jsonPath("$.list[4].title").value("제목96"))
                .andExpect(jsonPath("$.list[5].title").value("제목95"))
                .andExpect(jsonPath("$.list[6].title").value("제목94"))
                .andExpect(jsonPath("$.list[7].title").value("제목93"))
                .andExpect(jsonPath("$.list[8].title").value("제목92"))
                .andExpect(jsonPath("$.list[9].title").value("제목91"))
                .andExpect(jsonPath("$.list[10].title").value("제목90"))

                .andDo(print());




    }


    @Test
    @WithAuthUser(username = "test")
    @DisplayName("리스트 조회 페이지 + 검색")
    void 리스트조회_페이징_검색() throws Exception {

        User user = User.builder()
                .username("test")
                .password("password")
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();

        userRepository.save(user);


        IntStream.rangeClosed(1,100).forEach(
                i->{
                    quizRepository.save(Quiz.builder()
                            .title("제목" + i)
                            .user(user)
                            .content("내용" + i)
                            .questionCount(0)
                            .build());

                }
        );


        mockMvc.perform(get("/quiz?page=0&type=title&content=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPage").value(1L))
                .andExpect(jsonPath("$.list[0].title").value("제목100"))
                .andExpect(jsonPath("$.list[1].title").value("제목90"))
                .andExpect(jsonPath("$.list[2].title").value("제목80"))
                .andExpect(jsonPath("$.list[3].title").value("제목70"))
                .andExpect(jsonPath("$.list[4].title").value("제목60"))
                .andExpect(jsonPath("$.list[5].title").value("제목50"))
                .andExpect(jsonPath("$.list[6].title").value("제목40"))
                .andExpect(jsonPath("$.list[7].title").value("제목30"))
                .andExpect(jsonPath("$.list[8].title").value("제목20"))
                .andExpect(jsonPath("$.list[9].title").value("제목10"))

                .andDo(print());




    }



    @Test
    @WithAuthUser(username = "test")
    @DisplayName("퀴즈 수정")
    void 수정() throws Exception {

        Quiz quiz = Quiz.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .questionCount(0)
                .build();

        quizRepository.save(quiz);

        QuizEdit edit = new QuizEdit("제목수정", "내용수정");

        String json = objectMapper.writeValueAsString(edit);


        mockMvc.perform(patch("/quiz/{quizId}", quiz.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithAuthUser(username = "test")
    @DisplayName("퀴즈 삭제")
    void 삭제() throws Exception {

        Quiz quiz = Quiz.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .questionCount(0)
                .build();
        log.info("시작");
        quizRepository.save(quiz);

        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");


        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate("질문입니다.", "힌트없음", examples, "질문3");
        EssayQuestionCreate essay = new EssayQuestionCreate("질문2입니다.", "힌트없음", "주관식");
        questionService.addEssay(quiz.getId(), essay);
        questionService.addMultiple(quiz.getId(), multiple);





        mockMvc.perform(delete("/quiz/{quizId}", quiz.getId()))
                .andExpect(status().isOk())
                .andDo(print());
        log.info("끝");
    }

}