package com.quiz.service;

import com.quiz.domain.Quiz;
import com.quiz.domain.User;
import com.quiz.domain.comment.QuizComment;
import com.quiz.repository.CommentRepository;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.CommentCreate;
import com.quiz.request.CommentEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    CommentService commentService;

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
    @DisplayName("퀴즈 댓글 쓰기")
    void write(){

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

        CommentCreate commentCreate = new CommentCreate("content");


        commentService.writeQuizComment(quiz.getId(), user.getId(), commentCreate);


        assertThat(commentRepository.count()).isEqualTo(1L);
        assertThat(commentRepository.findAll().get(0).getContent()).isEqualTo("content");



    }

    @Test
    @DisplayName("퀴즈 댓글 수정")
    void edit(){


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

        QuizComment comment = QuizComment.builder()
                .content("content")
                .user(user)
                .quiz(quiz)
                .build();

        commentRepository.save(comment);

        CommentEdit commentEdit = new CommentEdit("update content");

        commentService.editQuizComment(comment.getId(), commentEdit);

        assertThat(commentRepository.findById(comment.getId()).get().getContent()).isEqualTo("update content");


    }

    @Test
    @DisplayName("퀴즈 댓글 삭제")
    void delete(){


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

        QuizComment comment = QuizComment.builder()
                .content("content")
                .user(user)
                .quiz(quiz)
                .build();

        commentRepository.save(comment);


        commentService.deleteQuizComment(comment.getId());

        assertThat(commentRepository.count()).isEqualTo(0L);
    }



}