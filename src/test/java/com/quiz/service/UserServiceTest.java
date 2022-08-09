package com.quiz.service;

import com.quiz.domain.User;
import com.quiz.exception.NicknameDuplicateException;
import com.quiz.exception.PasswordDiffException;
import com.quiz.exception.PasswordNotEqualException;
import com.quiz.repository.UserRepository;
import com.quiz.request.NicknameUpdate;
import com.quiz.request.PasswordUpdate;
import com.quiz.request.UserCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {


    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;


    @BeforeEach
    void clean(){
        userRepository.deleteAll();

    }


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

    @Test
    @DisplayName("비밀번호 변경")
    void 비밀번호_변경(){

        User user = User.builder()
                .username("test")
                .password(encoder.encode("password"))
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();

        userRepository.save(user);

        PasswordUpdate passwordUpdate = new PasswordUpdate("password", "change", "change");

        userService.passwordChange(user.getId(), passwordUpdate);

        User changeUser = userRepository.findById(user.getId()).get();


        assertThat(encoder.matches("change", changeUser.getPassword())).isTrue();



    }

    @Test
    @DisplayName("비밀번호 변경시 현재 비밀번호 다름")
    void 비밀번호_변경2(){

        User user = User.builder()
                .username("test")
                .password(encoder.encode("password"))
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();

        userRepository.save(user);

        PasswordUpdate passwordUpdate = new PasswordUpdate("password1", "change", "change");

        Assertions.assertThatThrownBy(() -> userService.passwordChange(user.getId(), passwordUpdate)).isInstanceOf(PasswordDiffException.class);




    }

    @Test
    @DisplayName("비밀번호 변경시 비밀번호와 비밀번호 확인이 다름")
    void 비밀번호_변경3(){

        User user = User.builder()
                .username("test")
                .password(encoder.encode("password"))
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();

        userRepository.save(user);

        PasswordUpdate passwordUpdate = new PasswordUpdate("password", "change", "changezz");

        Assertions.assertThatThrownBy(() -> userService.passwordChange(user.getId(), passwordUpdate)).isInstanceOf(PasswordNotEqualException.class);


    }


    @Test
    @DisplayName("닉네임 변경")
    void 닉네임_변경(){

        User user = User.builder()
                .username("test")
                .password(encoder.encode("password"))
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();


        userRepository.save(user);


        NicknameUpdate nicknameUpdate = new NicknameUpdate("change");

        userService.nicknameChange(user.getId(), nicknameUpdate);

        User changeUser = userRepository.findById(user.getId()).get();

        assertThat(changeUser.getNickname()).isEqualTo("change");



    }


    @Test
    @DisplayName("닉네임 변경시 중복된 닉네임이 있는 경우")
    void 닉네임_변경2(){

        User user = User.builder()
                .username("test")
                .password(encoder.encode("password"))
                .nickname("nickname")
                .email("test@naver.com")
                .role("USER")
                .build();


        userRepository.save(user);


        NicknameUpdate nicknameUpdate = new NicknameUpdate("nickname");

        assertThatThrownBy(() -> userService.nicknameChange(user.getId(), nicknameUpdate)).isInstanceOf(NicknameDuplicateException.class);



    }



}