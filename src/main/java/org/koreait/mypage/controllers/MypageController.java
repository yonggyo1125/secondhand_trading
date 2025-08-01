package org.koreait.mypage.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.board.controllers.BoardSearch;
import org.koreait.board.entities.BoardData;
import org.koreait.board.services.BoardInfoService;
import org.koreait.global.annotations.ApplyCommonController;
import org.koreait.global.libs.Utils;
import org.koreait.global.search.ListData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ApplyCommonController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final BoardInfoService boardInfoService;
    private final Utils utils;

    @GetMapping
    public String index() {
        return utils.tpl("mypage/index");
    }

    @GetMapping("/board")
    public String board(@ModelAttribute BoardSearch search, Model model) {
        ListData<BoardData> data = boardInfoService.getMyList(search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return utils.tpl("mypage/board");
    }

    private void commonProcess(String mode, Model model) {

    }
}
