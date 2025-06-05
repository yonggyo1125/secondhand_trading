package org.koreait.admin.trend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/trend")
public class TrendController {

    @GetMapping({"", "/news"}) // /admin/trend, /admin/trend/news
    public String news() {

        return "admin/trend/news";
    }
}
