package com.quiz.controller.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.controller.security.WithAuthUser;
import com.quiz.domain.Quiz;
import com.quiz.domain.question.Question;
import com.quiz.repository.CommentRepository;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.EssayQuestionCreate;
import com.quiz.request.EssayQuestionEdit;
import com.quiz.request.MultipleChoiceQuestionCreate;
import com.quiz.request.MultipleChoiceQuestionEdit;
import com.quiz.service.QuestionService;
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

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.quiz.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
class QuestionControllerDocTest {


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

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void clean(){
        questionRepository.deleteAll();
        commentRepository.deleteAll();
        quizRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    @WithAuthUser(username = "question")
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

        mockMvc.perform(RestDocumentationRequestBuilders.post("/question/multiple/{quizId}", quiz.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(document("multiple-create",
                        pathParameters(parameterWithName("quizId").description("퀴즈 아이디")),
                                requestFields(
                                        fieldWithPath("content").description("질문 내용"),
                                        fieldWithPath("hint").description("질문 힌트"),
                                        fieldWithPath("examples[0]").description("보기 1"),
                                        fieldWithPath("examples[1]").description("보기 2"),
                                        fieldWithPath("examples[2]").description("보기 3"),
                                        fieldWithPath("examples[3]").description("보기 4"),
                                        fieldWithPath("answer").description("정답")
                                )))
                .andDo(print());


    }


    @Test
    @WithAuthUser(username = "question")
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

        MultipleChoiceQuestionEdit edit = new MultipleChoiceQuestionEdit("수정입니다.", "힌트수정", "수정3", change);

        String json = objectMapper.writeValueAsString(edit);
        List<Question> list = questionRepository.findAll();
        Question question = list.get(0);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/question/multiple/{questionId}", question.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(document("multiple-edit",
                        pathParameters(parameterWithName("questionId").description("질문 ID")),
                        requestFields(
                                fieldWithPath("content").description("수정할 내용"),
                                fieldWithPath("hint").description("수정할 힌트"),
                                fieldWithPath("answer").description("수정할 정답"),
                                fieldWithPath("examples[0]").description("수정할 보기1"),
                                fieldWithPath("examples[1]").description("수정할 보기2"),
                                fieldWithPath("examples[2]").description("수정할 보기3"),
                                fieldWithPath("examples[3]").description("수정할 보기4")
                                )

                ))
                .andDo(print());



    }





    @Test
    @WithAuthUser(username = "question")
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
        mockMvc.perform(RestDocumentationRequestBuilders.post("/question/essay/{quizId}", quiz.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(document("essay-create",
                        pathParameters(parameterWithName("quizId").description("퀴즈 ID")),
                        requestFields(
                                fieldWithPath("content").description("질문 내용"),
                                fieldWithPath("hint").description("힌트 내용"),
                                fieldWithPath("answer").description("정답")
                        )
                ))
                .andExpect(status().isOk())
                .andDo(print());



    }


    @Test
    @WithAuthUser(username = "question")
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

        EssayQuestionEdit edit = new EssayQuestionEdit("수정입니다.", "힌트수정", "수정이");

        String json = objectMapper.writeValueAsString(edit);
        List<Question> list = questionRepository.findAll();
        Question question = list.get(0);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/question/essay/{questionId}", question.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(document("essay-edit",
                        pathParameters(parameterWithName("questionId").description("질문 ID")),
                        requestFields(
                                fieldWithPath("content").description("수정할 내용"),
                                fieldWithPath("hint").description("수정할 힌트"),
                                fieldWithPath("answer").description("수정할 정답")

                        )
                ))
                .andExpect(status().isOk())
                .andDo(print());


    }


}