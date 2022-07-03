package com.quiz.domain.question;

import com.quiz.Entity.BaseEntity;
import com.quiz.domain.Quiz;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public abstract class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private int number;

    private String content;

    private String hint;

    private String answer;

    public Question(Quiz quiz, int number, String content, String hint,String answer) {
        this.quiz = quiz;
        this.number = number;
        this.content = content;
        this.hint = hint;
        this.answer = answer;
    }
}
