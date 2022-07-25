package com.quiz.repository;

import com.quiz.domain.Quiz;
import com.quiz.domain.comment.Comment;
import com.quiz.domain.comment.QuizComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    @Query(value = "select q from QuizComment q join fetch q.user where q.quiz =:quiz",
            countQuery = "select q from QuizComment q where q.quiz =:quiz")
    Page<QuizComment> findQuizComments(@Param("quiz") Quiz quiz, Pageable pageable);

}
