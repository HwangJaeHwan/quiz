package com.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.controller.security.WithAuthUser;
import com.quiz.domain.Quiz;
import com.quiz.domain.User;
import com.quiz.repository.CommentRepository;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.EssayQuestionCreate;
import com.quiz.request.MultipleChoiceQuestionCreate;
import com.quiz.request.QuizCreate;
import com.quiz.service.QuestionService;
import com.quiz.service.QuizService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.quiz.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class QuizControllerDocTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private QuestionService questionService;

    @AfterEach
    void clean(){
        questionRepository.deleteAll();
        commentRepository.deleteAll();
        quizRepository.deleteAll();
        userRepository.deleteAll();

    }


    @Test
    @WithAuthUser(username = "test")
    @DisplayName("퀴즈 단건조회")
    void 퀴즈단건() throws Exception {

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


        mockMvc.perform(RestDocumentationRequestBuilders.get("/quiz/{quizId}", quizList.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("quiz-inquiry", pathParameters(
                        parameterWithName("quizId").description("퀴즈 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("퀴즈 ID"),
                                fieldWithPath("title").description("퀴즈 제목"),
                                fieldWithPath("content").description("퀴즈 내용"),
                                fieldWithPath("questions.[].id").description("질문 ID"),
                                fieldWithPath("questions.[].content").description("질문 내용"),
                                fieldWithPath("questions.[].questionType").description("질문 타입"),
                                fieldWithPath("questions.[].hint").description("질문 힌트"),
                                fieldWithPath("questions.[].examples").description("주관식 질문 보기"),
                                fieldWithPath("questions.[].answer").description("질문 정답")

                                )

                ));


    }

    @Test
    @WithAuthUser(username = "test")
    @DisplayName("퀴즈 등록")
    void 등록테스트() throws Exception {



        QuizCreate quizCreate = new QuizCreate("제목입니다", "내용입니다.");

        String json = objectMapper.writeValueAsString(quizCreate);


        mockMvc.perform(RestDocumentationRequestBuilders.post("/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(document("quiz-create",
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용")
                        )
                        ))
                .andDo(print());

    }


    @Test
    @WithAuthUser(username = "test")
    @DisplayName("퀴즈 리스트 조회")
    void 퀴즈리스트() throws Exception {

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
                            .title("title" + i)
                            .user(user)
                            .content("content" + i)
                            .questionCount(0)
                            .build());

                }
        );


        mockMvc.perform(RestDocumentationRequestBuilders.get("/quiz?page=0&type=title&content=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("quiz-list",
                        requestParameters(parameterWithName("page").description("페이지"),
                                parameterWithName("type").description("검색 타입"),
                                parameterWithName("content").description("검색 내용")
                        ),
                        responseFields(fieldWithPath("totalPage").description("총 페이지"),
                                fieldWithPath("quizInfo.[].id").description("퀴즈 ID"),
                                fieldWithPath("quizInfo.[].title").description("퀴즈 제목"),
                                fieldWithPath("quizInfo.[].nickname").description("작성자 닉네임"),
                                fieldWithPath("quizInfo.[].questionCount").description("문제 수"),
                                fieldWithPath("quizInfo.[].createdTime").description("작성 시간")
                        )
                ))
                .andDo(print());




    }





}
