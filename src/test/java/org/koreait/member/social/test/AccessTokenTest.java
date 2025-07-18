package org.koreait.member.social.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
public class AccessTokenTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void test() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "29ffd877be5f6a7f4d4eb446b694e494");
        body.add("redirect_uri", "http://localhost:3000/member/social/callback/kakao");
        body.add("code", "");

    }
}
