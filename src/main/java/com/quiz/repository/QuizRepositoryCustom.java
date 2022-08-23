package com.quiz.repository;

import com.quiz.domain.Quiz;
import com.quiz.request.PageDTO;
import com.quiz.request.SearchDTO;
import org.springframework.data.domain.Page;


public interface QuizRepositoryCustom {


    Page<Quiz> quizList(PageDTO pageDTO, SearchDTO searchDTO);

}
