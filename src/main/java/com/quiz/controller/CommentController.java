package com.quiz.controller;

import com.quiz.auth.UserInfo;
import com.quiz.request.CommentCreate;
import com.quiz.request.PageDTO;
import com.quiz.response.QuizCommentListResponse;
import com.quiz.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/quiz/{quizId}")
    public Page<QuizCommentListResponse> quizComments(@PathVariable Long quizId, @RequestBody PageDTO pageDTO){

        return commentService.quizComments(quizId, pageDTO.getPageable());


    }

    @PostMapping("/quiz/{quizId}")
    public void writeQuiz(@AuthenticationPrincipal UserInfo userInfo, @PathVariable Long quizId, @RequestBody CommentCreate commentCreate) {


        commentService.writeQuizComment(quizId, userInfo.getUser().getId(),commentCreate);



    }








}
