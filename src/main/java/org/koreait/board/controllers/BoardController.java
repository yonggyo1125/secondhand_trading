package org.koreait.board.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.board.entities.Board;
import org.koreait.board.services.configs.BoardConfigInfoService;
import org.koreait.global.annotations.ApplyCommonController;
import org.koreait.global.libs.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@ApplyCommonController
@RequiredArgsConstructor
@RequestMapping("/board")
@SessionAttributes({"board"})
public class BoardController {

    private final Utils utils;
    private final BoardConfigInfoService configInfoService;

    @ModelAttribute("board")
    public Board getBoard() {
        return new Board();
    }

    // 게시글 목록
    @GetMapping("/list/{bid}")
    public String list(@PathVariable("bid") String bid, @ModelAttribute BoardSearch search, Model model) {
        commonProcess(bid, "list", model);

        return utils.tpl("board/list");
    }

    // 게시글 작성
    @GetMapping("/write/{bid}")
    public String write(@PathVariable("bid") String bid, Model model) {
        commonProcess(bid, "write", model);

        return utils.tpl("board/write");
    }

    // 게시글 수정
    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "update", model);

        return utils.tpl("board/update");
    }

    // 게시글 보기
    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "view", model);

        return utils.tpl("board/view");
    }

    // 게시글 삭제
    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, Model model, @SessionAttribute("board") Board board) {
        commonProcess(seq, "delete", model);

        return "redirect:/board/list/" + board.getBid();
    }

    /**
     * bid 기준의 공통 처리
     *  - 게시글 설정조회가 공통 처리
     *
     * @param bid
     * @param mode
     * @param model
     */
    private void commonProcess(String bid, String mode, Model model) {
        Board board = configInfoService.get(bid);
        mode = StringUtils.hasText(mode) ? mode : "list";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        String pageTitle = board.getName(); // 게시판 명


        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("board", board);
    }

    /**
     * seq 기준의 공통 처리
     *  - 게시글 조회가 공통 처리 ...
     * @param seq
     * @param mode
     * @param model
     */
    private void commonProcess(Long seq, String mode, Model model) {

    }
}
