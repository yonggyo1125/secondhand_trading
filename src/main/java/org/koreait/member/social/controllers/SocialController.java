package org.koreait.member.social.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.global.annotations.ApplyCommonController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@ApplyCommonController
@RequiredArgsConstructor
@RequestMapping("/member/social")
public class SocialController {

    @GetMapping("/callback/kakao")
    public String callback(@RequestParam("code") String code) {

        return null;
    }
}
