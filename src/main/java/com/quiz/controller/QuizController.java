package com.quiz.controller;

import com.quiz.request.QuizCreate;
import com.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public void quiz(@PathVariable Long quizId){


    }


}
