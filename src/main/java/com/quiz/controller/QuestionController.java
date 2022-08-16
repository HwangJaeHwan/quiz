package com.quiz.controller;

import com.quiz.request.EssayQuestionCreate;
import com.quiz.request.MultipleChoiceQuestionCreate;
import com.quiz.request.EssayQuestionUpdate;
import com.quiz.request.MultipleChoiceQuestionUpdate;
import com.quiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/question")
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

    @PatchMapping("/multiple/{questionId}")
    public void updateMultiple(@PathVariable Long questionId, @RequestBody MultipleChoiceQuestionUpdate update) {

        questionService.updateMultiple(questionId, update);
    }

    @PatchMapping("/essay/{questionId}")
    public void updateEssay(@PathVariable Long questionId, @RequestBody EssayQuestionUpdate update) {

        questionService.updateEssay(questionId, update);
    }







}
