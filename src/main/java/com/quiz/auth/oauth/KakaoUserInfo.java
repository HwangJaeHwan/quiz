package com.quiz.auth.oauth;

import lombok.Data;

import java.util.Map;

@Data
public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;
    private Map<String,Object> account;
    private Map<String, Object> profile;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.account = (Map<String, Object>) attributes.get("kakao_account");
        this.profile = (Map<String, Object>) attributes.get("properties");


    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String) account.get("email");
    }

    @Override
    public String getName() {
        return (String) profile.get("nickname");
    }
}
