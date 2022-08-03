package com.quiz.service;

import com.quiz.domain.User;
import com.quiz.exception.NicknameDuplicateException;
import com.quiz.exception.PasswordDiffException;
import com.quiz.exception.PasswordNotEqualException;
import com.quiz.exception.UserNotFound;
import com.quiz.repository.UserRepository;
import com.quiz.request.NicknameUpdate;
import com.quiz.request.PasswordUpdate;
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


    }


    public void passwordChange(Long userId, PasswordUpdate passwordUpdate) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);

        if (!encoder.matches(passwordUpdate.getNowPassword(), user.getPassword())) {
            throw new PasswordDiffException();
        }

        if (!passwordUpdate.getChangePassword().equals(passwordUpdate.getPasswordCheck())) {
            throw new PasswordNotEqualException();
        }


        user.changePassword(encoder.encode(passwordUpdate.getChangePassword()));


    }

    public void nicknameChange(Long userId, NicknameUpdate nicknameUpdate) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);

        if (userRepository.findByNickname(nicknameUpdate.getNickname()).isPresent()) {
            throw new NicknameDuplicateException();
        }

        user.changeNickname(nicknameUpdate.getNickname());


    }






}
