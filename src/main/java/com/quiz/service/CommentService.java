package com.quiz.service;

import com.quiz.domain.Quiz;
import com.quiz.domain.User;
import com.quiz.domain.comment.Comment;
import com.quiz.domain.comment.QuizComment;
import com.quiz.exception.CommentNotFound;
import com.quiz.exception.QuizNotFound;
import com.quiz.exception.UserNotFound;
import com.quiz.repository.CommentRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.CommentCreate;
import com.quiz.request.CommentEdit;
import com.quiz.response.QuizCommentListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;


    public boolean checkCommentOwner(Long commentId, User user) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFound::new);


        if (!comment.getUser().equals(user)) {
            return false;
        }

        return true;


    }




    public void writeQuizComment(Long quizId, Long userId, CommentCreate commentCreate){

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(QuizNotFound::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);

        commentRepository.save(
                QuizComment.builder()
                        .quiz(quiz)
                        .user(user)
                        .content(commentCreate.getContent())
                        .build()
        );


    }


    public void editQuizComment(Long quizCommentId, CommentEdit commentEdit){

        Comment comment = commentRepository.findById(quizCommentId).orElseThrow(CommentNotFound::new);

        comment.editContent(commentEdit);

    }


    public void deleteQuizComment(Long quizCommentId){

        commentRepository.deleteById(quizCommentId);

    }

    public List<QuizCommentListResponse> quizComments(Long quizId, Pageable pageable) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(QuizNotFound::new);
        log.info("시발놈");

        Page<QuizComment> comments = commentRepository.findQuizComments(quiz, pageable);

        log.info("now page={}", comments.getNumber());
        log.info("page size={}", comments.getContent().size());
        log.info("page elements={}", comments.getTotalElements());
        log.info("total page={}", comments.getTotalPages());

        return comments.stream().map(QuizCommentListResponse::new).collect(Collectors.toList());

    }





}
