package com.quiz.controller;

import com.quiz.auth.UserInfo;
import com.quiz.exception.NotCommentOwnerException;
import com.quiz.request.CommentCreate;
import com.quiz.request.CommentEdit;
import com.quiz.request.PageDTO;
import com.quiz.response.QuizCommentListResponse;
import com.quiz.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/quiz/{quizId}")
    public List<QuizCommentListResponse> quizComments(@PathVariable Long quizId, PageDTO pageDTO){

        log.info("pageDTO =", pageDTO.getPage());

        log.info("시발새기");


        return commentService.quizComments(quizId, pageDTO.getPageable());


    }

    @PostMapping("/quiz/{quizId}")
    public void writeQuizComment(@AuthenticationPrincipal UserInfo userInfo, @PathVariable Long quizId, @RequestBody @Valid CommentCreate commentCreate) {

        log.info("info id = {}", userInfo.getUser().getId());
        log.info("info username = {}", userInfo.getUser().getUsername());
        log.info("info nickname = {}", userInfo.getUser().getNickname());
        log.info("info email = {}", userInfo.getUser().getEmail());

        commentService.writeQuizComment(quizId, userInfo.getUser().getId(),commentCreate);

    }

    @PutMapping("/quiz/{quizCommentId}")
    public void editQuizComment(@AuthenticationPrincipal UserInfo userInfo, @PathVariable Long quizCommentId, @RequestBody @Valid CommentEdit commentEdit) {

        if (!commentService.checkCommentOwner(quizCommentId, userInfo.getUser())) {
            throw new NotCommentOwnerException();
        }

        commentService.editQuizComment(quizCommentId, commentEdit);
    }

    @DeleteMapping("/quiz/{quizCommentId}")
    public void deleteQuizComment(@AuthenticationPrincipal UserInfo userInfo, @PathVariable Long quizCommentId) {


        if (!commentService.checkCommentOwner(quizCommentId, userInfo.getUser())) {
            throw new NotCommentOwnerException();
        }

        commentService.deleteQuizComment(quizCommentId);



    }




}
