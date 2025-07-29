package org.koreait.board.services;

import lombok.RequiredArgsConstructor;
import org.koreait.board.controllers.BoardSearch;
import org.koreait.board.entities.BoardData;
import org.koreait.global.search.ListData;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class BoardInfoService {

    /**
     * 게시글 1개 조회
     *
     * @param seq
     * @return
     */
    public BoardData get(Long seq) {
        return null;
    }

    public ListData<BoardData> getList(BoardSearch search) {
        return null;
    }
}
