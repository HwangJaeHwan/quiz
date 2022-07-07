package com.quiz.controller;

import com.quiz.domain.Quiz;
import com.quiz.request.QuizCreate;
import com.quiz.response.QuizListResponse;
import com.quiz.response.QuizResponse;
import com.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/quiz")
    public void makeQuiz(@RequestBody @Valid QuizCreate request) {
        quizService.write(request);
    }

    @GetMapping("/quiz/{quizId}")
    public QuizResponse quiz(@PathVariable Long quizId){


        return quizService.quiz(quizId);

    }

    @GetMapping("/quiz")
    public List<QuizListResponse> quiz(){

        return quizService.getList();

    }


}
