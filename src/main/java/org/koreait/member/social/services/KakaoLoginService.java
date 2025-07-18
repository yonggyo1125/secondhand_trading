package org.koreait.member.social.services;

import lombok.RequiredArgsConstructor;
import org.koreait.global.exceptions.script.AlertRedirectException;
import org.koreait.global.libs.Utils;
import org.koreait.member.repositories.MemberRepository;
import org.koreait.member.social.constants.SocialType;
import org.koreait.member.social.entities.AuthToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

@Lazy
@Service
@RequiredArgsConstructor
public class KakaoLoginService implements SocialLoginService {
    private final Utils utils;
    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;

    @Value("${social.kakao.apikey}")
    private String apiKey;

    @Override
    public String getToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", apiKey);
        body.add("redirect_uri", utils.getUrl("/member/social/callback/kakao"));
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        String requestUrl = "https://kauth.kakao.com/oauth/token";

        ResponseEntity<AuthToken> response = restTemplate.exchange(URI.create(requestUrl), HttpMethod.POST, request, AuthToken.class);

        checkSuccess(response.getStatusCode());

        // access Token으로 회원정보 조회
        AuthToken authToken = response.getBody();
        requestUrl = "https://kapi.kakao.com/v2/user/me";
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(authToken.getAccessToken());

        request = new HttpEntity<>(headers);
        ResponseEntity<Map> res = restTemplate.exchange(URI.create(requestUrl), HttpMethod.POST, request, Map.class);

        checkSuccess(res.getStatusCode());

        Map resBody = res.getBody();
        long id = (Long)resBody.get("id");
        return "" + id;
    }

    @Override
    public boolean login(String token) {
        return true;
    }

    @Override
    public boolean exists(String token) {

        return memberRepository.existsBySocialTypeAndSocialToken(SocialType.KAKAO, token);
    }

    @Override
    public String getLoginUrl(String redirectUrl) {

        String redirectUri = utils.getUrl("/member/social/callback/kakao");

        return String.format("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=%s", apiKey, redirectUri, Objects.requireNonNullElse(redirectUrl, ""));
    }

    private void checkSuccess(HttpStatusCode status) {
        if (!status.is2xxSuccessful()) {
            throw new AlertRedirectException(utils.getMessage("UnAuthorized.social"), "/member/login", HttpStatus.UNAUTHORIZED);
        }
    }
}
