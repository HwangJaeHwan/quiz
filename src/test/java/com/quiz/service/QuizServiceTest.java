package com.quiz.service;

import com.quiz.domain.question.Question;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.request.*;
import com.quiz.response.QuizResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class QuizServiceTest {

    @Autowired
    QuizService quizService;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;


    @Test
    @DisplayName("quiz 등록")
    void test1() {

        List<String> examples = new ArrayList<>();

        examples.add("질문1");
        examples.add("질문2");
        examples.add("질문3");
        examples.add("질문4");

        List<MultipleChoiceQuestionCreate> multiple = new ArrayList<>();
        List<EssayQuestionCreate> essay = new ArrayList<>();

        multiple.add(new MultipleChoiceQuestionCreate(1, "질문입니다.", "힌트없음", examples, "질문3"));
        essay.add(new EssayQuestionCreate(2, "질문2입니다.", "힌트없음", "주관식"));


        QuizCreate quizCreate = new QuizCreate("제목입니다", "내용입니다.", essay, multiple);

        quizService.write(quizCreate);


        assertThat(1L).isEqualTo(quizRepository.count());
        assertThat(2L).isEqualTo(questionRepository.count());

    }


    @Test
    @DisplayName("quiz 조회")
    void test2() {

        List<String> examples = new ArrayList<>();

        examples.add("질문1");
        examples.add("질문2");
        examples.add("질문3");
        examples.add("질문4");

        List<MultipleChoiceQuestionCreate> multiple = new ArrayList<>();
        List<EssayQuestionCreate> essay = new ArrayList<>();

        multiple.add(new MultipleChoiceQuestionCreate(1, "질문입니다.", "힌트없음", examples, "질문3"));
        essay.add(new EssayQuestionCreate(2, "질문2입니다.", "힌트없음", "주관식"));


        QuizCreate quizCreate = new QuizCreate("제목입니다.", "내용입니다.", essay, multiple);

        quizService.write(quizCreate);

        QuizResponse quiz = quizService.quiz(1L);

        List<Question> all = questionRepository.findAll();



        assertThat(quiz.getTitle()).isEqualTo("제목입니다.");
        assertThat(quiz.getContent()).isEqualTo("내용입니다.");
        assertThat(quiz.getQuestions().size()).isEqualTo(2);

    }





}