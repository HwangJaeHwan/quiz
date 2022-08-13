package com.quiz.domain.question;

import com.quiz.Entity.BaseEntity;
import com.quiz.domain.Quiz;
import com.quiz.response.QuestionUpdate;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;



    private String content;

    private String hint;

    private String answer;

    public Question(Quiz quiz, String content, String hint,String answer) {
        this.quiz = quiz;
        this.content = content;
        this.hint = hint;
        this.answer = answer;
    }


    public void update(QuestionUpdate questionUpdate) {
        this.content = questionUpdate.getContent();
        this.hint = questionUpdate.getHint();
        this.answer = questionUpdate.getAnswer();
    }
}
