package com.quiz.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class Example {

    @NotBlank
    private Integer number;

    @NotBlank
    private String content;

}
