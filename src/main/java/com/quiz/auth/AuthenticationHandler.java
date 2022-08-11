package com.quiz.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    }



    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String message;

        response.setHeader("content-type", "application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);



        if (exception instanceof BadCredentialsException) {
            message = "아이디 또는 비밀번호가 맞지 않습니다.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = "아이디 또는 비밀번호가 맞지 않습니다.";
        } else {
            message = "로그인에 실패하였습니다.";
        }


        ErrorResponse errorResponse = new ErrorResponse("400", message);
        String json = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(json);

    }

}
