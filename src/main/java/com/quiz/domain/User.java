package com.quiz.domain;

import com.quiz.request.PasswordUpdate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String password;

    private String email;

    private String nickname;

    private String role;

    private String provider;

    private String providerId;

    @Builder
    public User(String username, String password, String email, String nickname, String role, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }


    public void changePassword(String password) {

        this.password = password;


    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}
