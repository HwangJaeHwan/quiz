package com.quiz.service;

import com.quiz.domain.Quiz;
import com.quiz.domain.User;
import com.quiz.domain.question.Question;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.*;
import com.quiz.response.QuestionResponse;
import com.quiz.response.QuizListResponse;
import com.quiz.response.QuizResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class QuizServiceTest {

    @Autowired
    QuizService quizService;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void clean(){
        questionRepository.deleteAll();
        quizRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    @DisplayName("quiz 등록")
    void test1() {

        User user = User.builder()
                .username("test")
                .password("password")
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();

        userRepository.save(user);



        QuizCreate quizCreate = new QuizCreate("제목입니다.", "내용입니다.");

        quizService.write(user.getId(), quizCreate);


        Quiz quiz = quizRepository.findAll().get(0);
        assertThat(quiz.getTitle()).isEqualTo("제목입니다.");
        assertThat(quiz.getContent()).isEqualTo("내용입니다.");
        assertThat(1L).isEqualTo(quizRepository.count());

    }


    @Test
    @DisplayName("quiz 조회")
    void 퀴즈조회() {


        User user = User.builder()
                .username("test")
                .password("password")
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();

        userRepository.save(user);


        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");


        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate("질문입니다.", "힌트없음", examples, "질문3");
        EssayQuestionCreate essay = new EssayQuestionCreate("질문2입니다.", "힌트없음", "주관식");


        QuizCreate quizCreate = new QuizCreate("제목입니다.", "내용입니다.");

        quizService.write(user.getId(), quizCreate);

        Quiz quiz = quizRepository.findAll().get(0);

        questionService.addMultiple(quiz.getId(), multiple);
        questionService.addEssay(quiz.getId(), essay);


        QuizResponse quizResponse = quizService.quiz(quiz.getId());

        assertThat(quizResponse.getTitle()).isEqualTo("제목입니다.");
        assertThat(quizResponse.getContent()).isEqualTo("내용입니다.");
        assertThat(quizResponse.getQuestions().size()).isEqualTo(2L);


    }

    @Test
    @DisplayName("퀴즈 리스트 조회")
    void 퀴즈리스트(){


        User user = User.builder()
                .username("test")
                .password("password")
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();

        userRepository.save(user);


        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");


        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate("질문입니다.", "힌트없음", examples, "질문3");
        EssayQuestionCreate essay = new EssayQuestionCreate("질문2입니다.", "힌트없음", "주관식");


        QuizCreate quizCreate = new QuizCreate("제목입니다.", "내용입니다.");
        QuizCreate quizCreate2 = new QuizCreate("제목입니다2.", "내용입니다2.");

        quizService.write(user.getId(), quizCreate);
        quizService.write(user.getId(), quizCreate2);

        questionService.addMultiple(1L, multiple);
        questionService.addEssay(1L, essay);

        questionService.addMultiple(2L, multiple);
        questionService.addEssay(2L, essay);


        List<QuizListResponse> list = quizService.getList();

        assertThat(list.size()).isEqualTo(2L);
        assertThat(list.get(0).getTitle()).isEqualTo("제목입니다.");
        assertThat(list.get(1).getTitle()).isEqualTo("제목입니다2.");



    }

    @Test
    @DisplayName("퀴즈 수정")
    void 수정(){

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


        QuizEdit edit = new QuizEdit("제목 수정", "내용 수정");

        quizService.edit(quiz.getId(), edit);

        Quiz changeQuiz = quizRepository.findById(quiz.getId()).get();

        assertThat(changeQuiz.getTitle()).isEqualTo("제목 수정");
        assertThat(changeQuiz.getContent()).isEqualTo("내용 수정");


    }

    @Test
    @DisplayName("퀴즈 삭제")
    void 삭제(){


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

        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");


        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate("질문입니다.", "힌트없음", examples, "질문3");
        EssayQuestionCreate essay = new EssayQuestionCreate("질문2입니다.", "힌트없음", "주관식");

        questionService.addMultiple(quiz.getId(), multiple);
        questionService.addEssay(quiz.getId(), essay);


        quizService.delete(quiz.getId());


        assertThat(quizRepository.count()).isEqualTo(0L);
        assertThat(questionRepository.count()).isEqualTo(0L);





    }




}