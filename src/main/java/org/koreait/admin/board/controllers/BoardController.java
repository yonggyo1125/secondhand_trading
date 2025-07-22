package org.koreait.admin.board.controllers;

import org.koreait.admin.global.controllers.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/board")
public class BoardController extends CommonController {

    @Override
    @ModelAttribute("mainCode")
    public String mainCode() {
        return "board";
    }

    /**
     * 게시판 목록
     * @return
     */
    @GetMapping({"", "/list"})
    public String list(Model model) {
        commonProcess("list", model);

        return "admin/board/list";
    }

    /**
     * 게시판 등록
     *
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String register(Model model) {
        commonProcess("register", model);

        return "admin/board/register";
    }

    /**
     * 컨트롤러 요청 처리 메서드의 공통 처리 부분
     * @param code
     * @param model
     */
    private void commonProcess(String code, Model model) {

    }
}
