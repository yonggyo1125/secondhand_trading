package org.koreait.admin.trend.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.admin.global.controllers.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/trend")
public class TrendController extends CommonController {

    @Override
    @ModelAttribute("mainCode")
    public String mainCode() {
        return "trend";
    }

    @GetMapping({"", "/news"}) // /admin/trend, /admin/trend/news
    public String news(Model model) {
        commonProcess("news", model);

        return "admin/trend/news";
    }

    @GetMapping("/etc")
    public String etc(Model model) {
        commonProcess("etc", model);

        return "admin/trend/etc";
    }

    /**
     * 공통 처리
     *
     * @param code : 서브메뉴 코드
     * @param model
     */
    private void commonProcess(String code, Model model) {


        model.addAttribute("subCode", code);
    }
}
