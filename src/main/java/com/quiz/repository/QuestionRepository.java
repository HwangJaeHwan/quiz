package com.quiz.repository;

import com.quiz.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Query("select q from Question q join fetch q.quiz qz where qz.id = :quizId order by q.createdTime")
    List<Question> findAllByQuiz(@Param("quizId") Long quizId);

}
