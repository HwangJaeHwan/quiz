package com.quiz.controller;

import com.quiz.request.EssayQuestionCreate;
import com.quiz.request.MultipleChoiceQuestionCreate;
import com.quiz.request.EssayQuestionEdit;
import com.quiz.request.MultipleChoiceQuestionEdit;
import com.quiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


    @PostMapping("/essay/{quizId}")
    public void writeEssay(@PathVariable Long quizId , @RequestBody @Valid EssayQuestionCreate request) {

        questionService.addEssay(quizId, request);

    }

    @PostMapping("/multiple/{quizId}")
    public void writeMultiple(@PathVariable Long quizId, @RequestBody @Valid MultipleChoiceQuestionCreate request){

        questionService.addMultiple(quizId, request);


    }

    @PatchMapping("/multiple/{questionId}")
    public void editMultiple(@PathVariable Long questionId, @RequestBody @Valid MultipleChoiceQuestionEdit edit) {

        questionService.editMultiple(questionId, edit);
    }

    @PatchMapping("/essay/{questionId}")
    public void editEssay(@PathVariable Long questionId, @RequestBody @Valid EssayQuestionEdit edit) {

        questionService.editEssay(questionId, edit);
    }







}
