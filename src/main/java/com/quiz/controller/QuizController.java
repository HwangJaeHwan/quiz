package com.quiz.controller;

import com.quiz.auth.UserInfo;
import com.quiz.domain.Quiz;
import com.quiz.request.QuizCreate;
import com.quiz.response.QuizListResponse;
import com.quiz.response.QuizResponse;
import com.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/quiz")
    public void makeQuiz(@AuthenticationPrincipal UserInfo userInfo, @RequestBody @Valid QuizCreate request) {
        log.info("id = {}",userInfo.getUser().getId());
        log.info("username = {}",userInfo.getUser().getUsername());
        log.info("nickname = {}", userInfo.getUser().getNickname());


        quizService.write(userInfo.getUser().getId(), request);
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
