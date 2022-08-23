package com.quiz.repository;

import com.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizRepositoryCustom {

}
