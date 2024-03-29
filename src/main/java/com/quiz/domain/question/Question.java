package com.quiz.domain.question;

import com.quiz.Entity.BaseEntity;
import com.quiz.domain.Quiz;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

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

    @ManyToOne(fetch = LAZY)
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


    void editContent(String content) {
        this.content = content;
    }

    void editHInt(String hint) {
        this.hint = hint;
    }

    void editAnswer(String answer){
        this.answer = answer;
    }

}
