package org.koreait.board.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.board.controllers.RequestBoard;
import org.koreait.board.entities.Board;
import org.koreait.board.entities.BoardData;
import org.koreait.board.repositories.BoardDataRepository;
import org.koreait.board.services.configs.BoardConfigInfoService;
import org.koreait.member.libs.MemberUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class BoardUpdateService {
    private final BoardConfigInfoService configInfoService;
    private final BoardDataRepository boardDataRepository;
    private final HttpServletRequest request;
    private final MemberUtil memberUtil;

    public BoardData process(RequestBoard form) {
        String bid = form.getBid();
        Long seq = form.getSeq();
        String gid = form.getGid();

        // 게시판 설정
        Board board = configInfoService.get(bid);

        BoardData item = null;
        if (seq != null && seq > 0L && (item = boardDataRepository.findById(seq).orElse(null))!= null) { // 글 수정

        } else { // 글 등록
            // 글 등록시에만 추가되는 부분을 여기에서 값을 설정
            /**
             * 1. 글을 작성한 회원
             * 2. 게시판 설정
             * 3. gid
             * 4. 아이피 정보(ipAddr) & 브라우저 정보(요청 헤더 - User-Agent)
             */
            item = new BoardData();
            item.setBoard(board);
            item.setGid(gid);
            item.setMember(memberUtil.getMember());
            item.setIp(request.getRemoteAddr());
            item.setUa(request.getHeader("User-Agent"));
        }

        return null;
    }
}
