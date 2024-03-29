package com.quiz.domain.question;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Example {

    @Id
    @GeneratedValue
    @Column(name = "example_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    private Question question;


    @Builder
    public Example(String content, Question question) {
        this.content = content;
        this.question = question;
    }
}
