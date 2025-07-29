package org.koreait.board.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.koreait.board.controllers.BoardSearch;
import org.koreait.board.entities.Board;
import org.koreait.board.entities.BoardData;
import org.koreait.board.entities.QBoardData;
import org.koreait.board.exceptions.BoardDataNotFoundException;
import org.koreait.board.repositories.BoardDataRepository;
import org.koreait.board.services.configs.BoardConfigInfoService;
import org.koreait.file.services.FileInfoService;
import org.koreait.global.search.ListData;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class BoardInfoService {

    private final BoardConfigInfoService configInfoService;
    private final BoardDataRepository boardDataRepository;
    private final FileInfoService fileInfoService;
    private final JPAQueryFactory queryFactory;

    /**
     * 게시글 1개 조회
     *
     * @param seq
     * @return
     */
    public BoardData get(Long seq) {
        BoardData item = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);

        // 추가 정보 처리
        addInfo(item);

        return item;
    }

    public ListData<BoardData> getList(BoardSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        List<String> bids = search.getBid();
        Board board = null;
        if (bids.size() == 1) { // 게시판 아이디가 1개인 경우 게시판 설정 조회
            board = configInfoService.get(bids.getFirst());

            // 한페이지당 게시글 갯수
            limit = board.getRowsForPage();
        }

        limit = limit < 1 ? 20 : limit;
        int offset = (page - 1) * limit; // 레코드 시작 번호

        /* 검색 조건 처리 S */
        List<String> categories = search.getCategory();
        String sopt = search.getSopt();
        String skey = search.getSkey();
        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();

        BooleanBuilder andBuilder = new BooleanBuilder();
        QBoardData boardData = QBoardData.boardData;

        if (bids != null && !bids.isEmpty())  { // 게시판 아이디 조회
            andBuilder.and(boardData.board.bid.in(bids));
        }

        if (categories != null && !categories.isEmpty()) { // 게시판 분류 조회
            andBuilder.and(boardData.category.in(categories));
        }

        // 게시글 등록일 조회
        if (sDate != null) {
            andBuilder.and(boardData.createdAt.goe(sDate.atStartOfDay()));
        }

        if (eDate != null) {
            andBuilder.and(boardData.createdAt.loe(eDate.atTime(23, 59, 59)));
        }

        /**
         * 키워드 검색
         * sopt - ALL : 통합검색 (SUBJECT + CONTENT + NAME)
         *        SUBJECT : 게시글 제목
         *        CONTENT : 게시글 내용
         *        SUBJECT_CONTENT : 게시글 제목 + 내용
         *        NAME : 작성자명(poster) + 회원명(name) + 이메일(email)
         */
        sopt = StringUtils.hasText(sopt) ? sopt.toUpperCase() : "ALL";
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();

            StringExpression subject = boardData.subject;
            StringExpression content = boardData.content;
            StringExpression name = boardData.poster.concat(boardData.member.name)
                    .concat(boardData.member.email);

            StringExpression fields = null;
            if (sopt.equals("SUBJECT")) {
                fields = subject;
            } else if (sopt.equals("CONTENT")) {
                fields = content;
            } else if (sopt.equals("SUBJECT_CONTENT")) {
                fields = subject.concat(content);
            } else if (sopt.equals("NAME")) {
                fields = name;
            } else { // 통합검색
                fields = subject.concat(content).concat(name);
            }
        }


        /* 검색 조건 처리 E */

        return null;
    }

    /**
     * 추가 정보 처리
     *
     * @param item
     */
    private void addInfo(BoardData item) {
        String gid = item.getGid();

        // 첨부된 이미지 & 파일 목록
        item.setEditorImages(fileInfoService.getList(gid, "editor"));
        item.setAttachFiles(fileInfoService.getList(gid, "attach"));
    }
}
