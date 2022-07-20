package com.quiz.service;

import com.quiz.exception.PasswordNotEqualException;
import com.quiz.repository.UserRepository;
import com.quiz.request.UserCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {


    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @Test
    @DisplayName("회원가입")
    void 저장(){

        UserCreate userCreate = new UserCreate("testId", "password", "password", "nickname", "email");

        userService.save(userCreate);

        assertThat(userRepository.count()).isEqualTo(1L);


    }

    @Test
    @DisplayName("비밀번호 다름")
    void 저장_비밀번호_다름(){
        UserCreate userCreate = new UserCreate("testId", "password", "password2", "nickname", "email");


        assertThatThrownBy(() -> userService.save(userCreate)).isInstanceOf(PasswordNotEqualException.class);


    }



}