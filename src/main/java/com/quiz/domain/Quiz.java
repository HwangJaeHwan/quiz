package com.quiz.domain;

import com.quiz.Entity.BaseEntity;
import com.quiz.request.QuizEdit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    private String content;

    private int questionCount;

    @Builder
    public Quiz(String title, User user, String content, int questionCount) {
        this.title = title;
        this.user = user;
        this.content = content;
        this.questionCount = questionCount;
    }

    public void addQuestionCount(){
        questionCount++;
    }


    public void edit(QuizEdit quizEdit) {

        this.title = quizEdit.getTitle();
        this.content = quizEdit.getContent();


    }
}
