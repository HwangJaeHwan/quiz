package com.quiz.service;

import com.quiz.domain.User;
import com.quiz.exception.NicknameDuplicateException;
import com.quiz.exception.PasswordDiffException;
import com.quiz.exception.PasswordNotEqualException;
import com.quiz.exception.UserNotFound;
import com.quiz.repository.UserRepository;
import com.quiz.request.NicknameEdit;
import com.quiz.request.PasswordEdit;
import com.quiz.request.UserCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    public void save(UserCreate userCreate){

        if (!userCreate.getPassword().equals(userCreate.getPasswordCheck())) {
            throw new PasswordNotEqualException();
        }

        if (userRepository.findByNickname(userCreate.getNickname()).isPresent()) {
            throw new NicknameDuplicateException();
        }


        User user = User.builder()
                .username(userCreate.getLoginId())
                .password(encoder.encode(userCreate.getPassword()))
                .nickname(userCreate.getNickname())
                .email(userCreate.getEmail())
                .role("USER")
                .build();

        userRepository.save(user);

        log.info("아이디 ={}", user.getId());


    }


    public void passwordChange(Long userId, PasswordEdit passwordEdit) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);

        if (!encoder.matches(passwordEdit.getNowPassword(), user.getPassword())) {
            throw new PasswordDiffException();
        }

        if (!passwordEdit.getChangePassword().equals(passwordEdit.getPasswordCheck())) {
            throw new PasswordNotEqualException();
        }

        String password = encoder.encode(passwordEdit.getChangePassword());
        log.info("change = {}", password);

        user.changePassword(password);

        log.info("change Password = {}", user.getPassword());

    }

    public void nicknameChange(Long userId, NicknameEdit nicknameEdit) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);

        if (userRepository.findByNickname(nicknameEdit.getNickname()).isPresent()) {
            throw new NicknameDuplicateException();
        }

        user.changeNickname(nicknameEdit.getNickname());


    }






}
