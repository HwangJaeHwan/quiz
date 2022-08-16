package com.quiz.service;

import com.quiz.domain.Quiz;
import com.quiz.domain.question.EssayQuestion;
import com.quiz.domain.question.MultipleChoiceQuestion;
import com.quiz.domain.question.Question;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.request.EssayQuestionCreate;
import com.quiz.request.MultipleChoiceQuestionCreate;
import com.quiz.request.EssayQuestionUpdate;
import com.quiz.request.MultipleChoiceQuestionUpdate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
class QuestionServiceTest {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionService questionService;

    @BeforeEach
    void clean(){
        questionRepository.deleteAll();
        quizRepository.deleteAll();

    }




    @Test
    @DisplayName("객관식 등록")
    void 객관식(){

        Quiz quiz = Quiz.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .questionCount(0)
                .build();

        quizRepository.save(quiz);

        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");

        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate("질문입니다.", "힌트없음", examples, "질문3");

        questionService.addMultiple(quiz.getId(), multiple);

        List<Question> list = questionRepository.findAll();

        assertThat(list.size()).isEqualTo(1L);
        MultipleChoiceQuestion question = (MultipleChoiceQuestion) list.get(0);
        assertThat(question).isInstanceOf(MultipleChoiceQuestion.class);
        assertThat(question.getContent()).isEqualTo("질문입니다.");
        assertThat(question.getHint()).isEqualTo("힌트없음");
        assertThat(question.getAnswer()).isEqualTo("질문3");
        assertThat(question.getExample1()).isEqualTo("질문1");
        assertThat(question.getExample2()).isEqualTo("질문2");
        assertThat(question.getExample3()).isEqualTo("질문3");
        assertThat(question.getExample4()).isEqualTo("질문4");


    }

    @Test
    @DisplayName("객관식 수정")
    void 객관식_수정(){


        Quiz quiz = Quiz.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .questionCount(0)
                .build();

        quizRepository.save(quiz);

        List<String> examples = List.of("질문1", "질문2", "질문3", "질문4");

        MultipleChoiceQuestionCreate multiple = new MultipleChoiceQuestionCreate("질문입니다.", "힌트없음", examples, "질문3");

        questionService.addMultiple(quiz.getId(), multiple);

        List<String> changeExamples = List.of("수정1", "수정2", "수정3", "수정4");

        Question question = questionRepository.findAll().get(0);

        MultipleChoiceQuestion multipleChoiceQuestion = questionRepository.findQuestionById(question.getId()).get();

        MultipleChoiceQuestionUpdate update = new MultipleChoiceQuestionUpdate("질문수정", "힌트수정", "정답수정", changeExamples);

        questionService.updateMultiple(multipleChoiceQuestion.getId(), update);


        MultipleChoiceQuestion changeQuestion = questionRepository.findQuestionById(question.getId()).get();

        assertThat(changeQuestion.getContent()).isEqualTo("질문수정");
        assertThat(changeQuestion.getHint()).isEqualTo("힌트수정");
        assertThat(changeQuestion.getAnswer()).isEqualTo("정답수정");
        assertThat(changeQuestion.getExample1()).isEqualTo("수정1");
        assertThat(changeQuestion.getExample2()).isEqualTo("수정2");
        assertThat(changeQuestion.getExample3()).isEqualTo("수정3");
        assertThat(changeQuestion.getExample4()).isEqualTo("수정4");


    }


    @Test
    @DisplayName("주관식 등록")
    void 주관식(){

        Quiz quiz = Quiz.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .questionCount(0)
                .build();

        quizRepository.save(quiz);

        EssayQuestionCreate essay = new EssayQuestionCreate("질문2입니다.", "힌트없음", "주관식");

        questionService.addEssay(quiz.getId(), essay);

        List<Question> list = questionRepository.findAll();

        assertThat(list.size()).isEqualTo(1L);
        assertThat(list.get(0)).isInstanceOf(EssayQuestion.class);


    }


    @Test
    @DisplayName("주관식 수정")
    void 주관식_수정(){

        Quiz quiz = Quiz.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .questionCount(0)
                .build();

        quizRepository.save(quiz);

        EssayQuestionCreate essay = new EssayQuestionCreate("질문2입니다.", "힌트없음", "주관식");

        questionService.addEssay(quiz.getId(), essay);

        EssayQuestionUpdate update = new EssayQuestionUpdate("수정입니다.", "힌트수정", "주관식수정");

        List<Question> list = questionRepository.findAll();

        Question question = list.get(0);
        questionService.updateEssay(question.getId(), update);

        EssayQuestion changeQuestion = questionRepository.findEssayQuestionById(question.getId()).get();

        assertThat(changeQuestion.getContent()).isEqualTo("수정입니다.");
        assertThat(changeQuestion.getHint()).isEqualTo("힌트수정");
        assertThat(changeQuestion.getAnswer()).isEqualTo("주관식수정");




    }


}