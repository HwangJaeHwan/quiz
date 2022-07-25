package com.quiz.service;

import com.quiz.domain.Quiz;
import com.quiz.domain.User;
import com.quiz.domain.comment.QuizComment;
import com.quiz.exception.QuizNotFound;
import com.quiz.exception.UserNotFound;
import com.quiz.repository.CommentRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.CommentCreate;
import com.quiz.response.QuizCommentListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;




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

    public Page<QuizCommentListResponse> quizComments(Long quizId, Pageable pageable) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(QuizNotFound::new);

        return commentRepository.findQuizComments(quiz, pageable).map(QuizCommentListResponse::new);

    }





}
