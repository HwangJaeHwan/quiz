package com.quiz.service;

import com.quiz.domain.User;
import com.quiz.exception.PasswordNotEqualException;
import com.quiz.repository.UserRepository;
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


        User user = User.builder()
                .username(userCreate.getLoginId())
                .password(encoder.encode(userCreate.getPassword()))
                .nickname(userCreate.getNickname())
                .email(userCreate.getEmail())
                .role("USER")
                .build();

        userRepository.save(user);


    }






}
