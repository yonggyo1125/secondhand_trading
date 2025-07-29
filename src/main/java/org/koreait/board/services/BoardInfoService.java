package org.koreait.board.services;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.koreait.board.controllers.BoardSearch;
import org.koreait.board.entities.Board;
import org.koreait.board.entities.BoardData;
import org.koreait.board.exceptions.BoardDataNotFoundException;
import org.koreait.board.repositories.BoardDataRepository;
import org.koreait.board.services.configs.BoardConfigInfoService;
import org.koreait.file.services.FileInfoService;
import org.koreait.global.search.ListData;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
