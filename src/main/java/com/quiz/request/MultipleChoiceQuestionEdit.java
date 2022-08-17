package com.quiz.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MultipleChoiceQuestionEdit {


    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private String hint;

    @NotBlank(message = "정답을 입력해주세요.")
    private String answer;

    @Size(min = 4, max = 4,message = "보기는 4개까지 생성가능합니다.")
    private List<String> examples;




}
