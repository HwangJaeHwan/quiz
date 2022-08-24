package com.quiz.service;

import com.quiz.domain.User;
import com.quiz.exception.NicknameDuplicateException;
import com.quiz.exception.PasswordDiffException;
import com.quiz.exception.PasswordNotEqualException;
import com.quiz.repository.CommentRepository;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.UserRepository;
import com.quiz.request.NicknameEdit;
import com.quiz.request.PasswordEdit;
import com.quiz.request.UserCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {


    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    QuizRepository quizRepository;


    @AfterEach
    void clean(){
        questionRepository.deleteAll();
        commentRepository.deleteAll();
        quizRepository.deleteAll();
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

        PasswordEdit passwordEdit = new PasswordEdit("password", "change", "change");

        userService.passwordChange(user.getId(), passwordEdit);

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

        PasswordEdit passwordEdit = new PasswordEdit("password1", "change", "change");

        Assertions.assertThatThrownBy(() -> userService.passwordChange(user.getId(), passwordEdit)).isInstanceOf(PasswordDiffException.class);




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

        PasswordEdit passwordEdit = new PasswordEdit("password", "change", "changezz");

        Assertions.assertThatThrownBy(() -> userService.passwordChange(user.getId(), passwordEdit)).isInstanceOf(PasswordNotEqualException.class);


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


        NicknameEdit nicknameEdit = new NicknameEdit("change");

        userService.nicknameChange(user.getId(), nicknameEdit);

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


        NicknameEdit nicknameEdit = new NicknameEdit("nickname");

        assertThatThrownBy(() -> userService.nicknameChange(user.getId(), nicknameEdit)).isInstanceOf(NicknameDuplicateException.class);



    }



}