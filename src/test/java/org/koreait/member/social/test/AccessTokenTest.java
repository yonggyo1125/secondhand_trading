package org.koreait.member.social.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


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
        body.add("code", "PHwx5T9YR5iIBB3nrohplJ1js2Z3fg9KttqewkoLBdhhAWO9337YAwAAAAQKFxItAAABmBuFGfqoblpFv_zasg");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);


        String requestUrl = "https://kauth.kakao.com/oauth/token";

        ResponseEntity<String> response = restTemplate.exchange(URI.create(requestUrl), HttpMethod.POST, request, String.class);

        HttpStatusCode status = response.getStatusCode();
        System.out.print("status:" + status);
        System.out.println(response.getBody());
    }
}
