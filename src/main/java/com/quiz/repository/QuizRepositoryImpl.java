package com.quiz.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiz.domain.QQuiz;
import com.quiz.domain.QUser;
import com.quiz.domain.Quiz;
import com.quiz.request.PageDTO;
import com.quiz.request.SearchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.LongSupplier;

import static com.quiz.domain.QQuiz.*;
import static com.quiz.domain.QUser.*;
import static org.springframework.util.StringUtils.*;

@Slf4j
@RequiredArgsConstructor
public class QuizRepositoryImpl  implements QuizRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Quiz> quizList(PageDTO pageDTO, SearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (hasText(searchDTO.getType()) && hasText(searchDTO.getContent())) {

            if (searchDTO.getType().equals("title")){
                builder.and(quiz.title.contains(searchDTO.getContent()));

            } else if (searchDTO.getType().equals("writer")) {
                builder.and(quiz.user.nickname.contains(searchDTO.getContent()));
            }
        }


        List<Quiz> content = jpaQueryFactory.selectFrom(quiz)
                .join(quiz.user, user).fetchJoin()
                .where(builder)
                .offset(pageDTO.getOffset())
                .limit(20)
                .orderBy(getOrder(pageDTO))
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(quiz.count())
                .from(quiz)
                .where(builder);


        return PageableExecutionUtils.getPage(content, PageRequest.of(pageDTO.getPage()-1, 20), countQuery::fetchOne);


    }


    private OrderSpecifier<?> getOrder(PageDTO pageDTO){

        switch (pageDTO.getSort()){
            case "createdTime":
                return new OrderSpecifier<>(Order.DESC, quiz.createdTime);

        }


      return new OrderSpecifier<>(Order.DESC, quiz.createdTime);

    }
}
