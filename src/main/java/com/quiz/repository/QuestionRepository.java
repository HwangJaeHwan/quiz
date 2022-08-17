package com.quiz.repository;

import com.quiz.domain.question.EssayQuestion;
import com.quiz.domain.question.MultipleChoiceQuestion;
import com.quiz.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Query("select q from Question q join fetch q.quiz qz where qz.id = :quizId order by q.createdTime")
    List<Question> findAllByQuizId(@Param("quizId") Long quizId);

    Optional<EssayQuestion> findEssayQuestionById(Long questionId);

    Optional<MultipleChoiceQuestion> findMultipleQuestionById(Long questionId);
    @Modifying
    @Query("delete from Question q where q.quiz.id = :quizId")
    void deleteAllByQuizId(@Param("quizId") Long quizId);
}
