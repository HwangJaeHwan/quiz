package com.quiz.domain.comment;

import com.quiz.Entity.BaseEntity;
import com.quiz.domain.User;
import com.quiz.request.CommentUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Comment extends BaseEntity {


    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(String content, User user) {
        this.content = content;
        this.user = user;
    }



    public void updateContent(CommentUpdate commentUpdate){
        this.content = commentUpdate.getContent();
    }
}
