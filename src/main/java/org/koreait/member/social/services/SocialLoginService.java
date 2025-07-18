package org.koreait.member.social.services;

public interface SocialLoginService {
    String getToken(String code);
    boolean login(String token);
    boolean exists(String token);
    String getLoginUrl();
}
