package com.quiz.controller;

import com.quiz.request.EssayQuestionCreate;
import com.quiz.request.MultipleChoiceQuestionCreate;
import com.quiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


    @PostMapping("/essay/{quizId}")
    public void writeEssay(@PathVariable Long quizId , @RequestBody EssayQuestionCreate request) {

        questionService.addEssay(quizId, request);

    }

    @PostMapping("/multiple/{quizId}")
    public void writeMultiple(@PathVariable Long quizId, @RequestBody MultipleChoiceQuestionCreate request){

        questionService.addMultiple(quizId, request);


    }





}
