package org.koreait.board.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.board.entities.Board;
import org.koreait.board.entities.BoardData;
import org.koreait.board.exceptions.GuestPasswordCheckException;
import org.koreait.board.services.*;
import org.koreait.board.services.configs.BoardConfigInfoService;
import org.koreait.board.validators.BoardValidator;
import org.koreait.file.constants.FileStatus;
import org.koreait.file.services.FileInfoService;
import org.koreait.global.annotations.ApplyCommonController;
import org.koreait.global.exceptions.script.AlertException;
import org.koreait.global.libs.Utils;
import org.koreait.global.search.ListData;
import org.koreait.member.libs.MemberUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@ApplyCommonController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final Utils utils;
    private final MemberUtil memberUtil;
    private final BoardConfigInfoService configInfoService;
    private final BoardUpdateService updateService;
    private final BoardInfoService infoService;
    private final BoardDeleteService deleteService;
    private final BoardViewCountService viewCountService;
    private final BoardAuthService authService;
    private final FileInfoService fileInfoService;
    private final BoardValidator boardValidator;
    private final PasswordEncoder encoder;
    private final HttpSession session;

    // 게시글 목록
    @GetMapping("/list/{bid}")
    public String list(@PathVariable("bid") String bid, @ModelAttribute BoardSearch search, Model model) {
        commonProcess(bid, "list", model);

        ListData<BoardData> data = infoService.getList(bid, search);
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return utils.tpl("board/list");
    }

    // 게시글 작성
    @GetMapping("/write/{bid}")
    public String write(@PathVariable("bid") String bid, RequestBoard form, Model model) {
        commonProcess(bid, "write", model);
        form.setBid(bid);
        form.setGid(UUID.randomUUID().toString());

        if (memberUtil.isLogin()) {
            form.setPoster(memberUtil.getMember().getName());
        } else {
            form.setGuest(true);
        }

        return utils.tpl("board/write");
    }

    // 게시글 수정
    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "update", model);
        RequestBoard form = infoService.getForm(seq);
        model.addAttribute("requestBoard", form);

        return utils.tpl("board/update");
    }

    @PostMapping("/save")
    public String save(@Valid RequestBoard form, Errors errors, Model model) {
        String mode = form.getMode();
        commonProcess(form.getBid(), mode, model);
        Board board = (Board)model.getAttribute("board");

        if (!mode.equals("update")) { // 게시글 등록시
            form.setGuest(!memberUtil.isLogin());
        }

        boardValidator.validate(form, errors);

        if (errors.hasErrors()) {
            String gid = form.getGid();
            form.setEditorImages(fileInfoService.getList(gid, "editor", FileStatus.ALL));
            form.setAttachFiles(fileInfoService.getList(gid, "attach", FileStatus.ALL));

            return utils.tpl("board/" + mode);
        }

        // 게시글 저장 처리
        BoardData item = updateService.process(form);
        String redirectUrl = board.isAfterWritingRedirect() ? "view/" + item.getSeq() : "list/" + form.getBid();

        return "redirect:/board/" + redirectUrl;
    }

    // 게시글 보기
    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "view", model);

        Board board = (Board)model.getAttribute("board");

        if (board != null && board.isShowViewList()) { // 게시글 보기 하단에 목록 노출
            BoardSearch search = new BoardSearch();
            ListData<BoardData> data = infoService.getList(board.getBid(), search);
            model.addAttribute("items", data.getItems());
            model.addAttribute("pagination", data.getPagination());
            model.addAttribute("boardSearch", search);
        }

        // 게시글 조회수 업데이트
        viewCountService.update(seq);

        // 댓글 기본값 처리
        if (board != null && board.isComment()) {
            if (board.isCommentable()) { // 댓글 작성 가능 여부
                RequestComment commentForm = new RequestComment();
                if (memberUtil.isLogin()) { // 로그인 상태라면 로그인한 회원 이름으로 초기값
                    commentForm.setCommenter(memberUtil.getMember().getName());
                }

                model.addAttribute("requestComment", commentForm);
            }
        }

        return utils.tpl("board/view");
    }

    @PostMapping("/comment")
    public String comment(@Valid RequestComment form, Errors errors, Model model) {
        if (errors.hasErrors()) {

            for (Map.Entry<String, List<String>> entry : utils.getErrorMessages(errors).entrySet()) {
                String message = entry.getValue().getFirst();
                throw new AlertException(message, HttpStatus.BAD_REQUEST);
            }
        }
        // 댓글 작성 처리

        // 댓글 작성이 완료되면 부모창을 새로고침
        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
     }

    // 게시글 삭제
    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "delete", model);
        deleteService.process(seq);

        Board board = (Board)model.getAttribute("board");

        return "redirect:/board/list/" + board.getBid();
    }

    // 비회원 글수정, 글삭제 비밀번호 확인
    @ExceptionHandler(GuestPasswordCheckException.class)
    public String guestPassword(Model model) {
        model.addAttribute("isLogin", memberUtil.isLogin());
        model.addAttribute("isAdmin", memberUtil.isAdmin());
        model.addAttribute("loggedMember", memberUtil.getMember());
        model.addAttribute("pageTitle", utils.getMessage("비회원_비밀번호_확인"));
        return utils.tpl("board/password");
    }

    // 비회원 글수정, 글삭제 비밀번호 확인
    @PostMapping("/password")
    public String guestPasswordCheck(@RequestParam(name="password", required = false) String password, Model model, @SessionAttribute("board_guest_seq") Long seq) {
        if (!StringUtils.hasText(password)) {
            throw new AlertException(utils.getMessage("비밀번호를_입력하세요."), HttpStatus.BAD_REQUEST);
        }

        BoardData item = infoService.get(seq);
        if (!encoder.matches(password, item.getGuestPw())) {
            throw new AlertException(utils.getMessage("비밀번호가_일치하지_않습니다."), HttpStatus.BAD_REQUEST);
        }

        session.setAttribute("board_seq_" + seq, true); // 비회원 비밀번호 확인 완료


        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
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

        authService.check(mode, bid); // 글쓰기, 글목록에서의 권한 체크

        mode = StringUtils.hasText(mode) ? mode : "list";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        String pageTitle = board.getName(); // 게시판 명

        String skin = board.getSkin();
        addCss.add("board/style"); // 스킨과 상관없는 공통 스타일
        addCss.add(String.format("board/%s/style", skin)); // 스킨별 스타일

        addScript.add("board/common"); // 스킨 상관없는 공통 자바스크립트

        if (mode.equals("write") || mode.equals("update")) {  // 등록, 수정
            if (board.isAttachFile() || (board.isImageUpload() && board.isEditor())) {
                addCommonScript.add("fileManager");
            }

            if (board.isEditor()) { // 에디터를 사용하는 경우, CKEDITOR5 스크립트를 추가
                addCommonScript.add("ckeditor5/ckeditor");
            }

            addScript.add(String.format("board/%s/form", skin)); // 스킨별 양식 관련 자바스크립트
        } else if (mode.equals("view")) { // 게시글 보기
            BoardData item = (BoardData)model.getAttribute("item");
            pageTitle = item.getSubject() + " - " + pageTitle;
        }

        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("board", board);
        model.addAttribute("mode", mode);
    }

    /**
     * seq 기준의 공통 처리
     *  - 게시글 조회가 공통 처리 ...
     * @param seq
     * @param mode
     * @param model
     */
    private void commonProcess(Long seq, String mode, Model model) {
        BoardData item = infoService.get(seq);
        model.addAttribute("item", item);

        authService.check(mode, seq); // 글보기, 글수정시 권한 체크

        Board board = item.getBoard();
        commonProcess(board.getBid(), mode, model);
    }

}
