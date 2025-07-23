package org.koreait.board.services.configs;

import lombok.RequiredArgsConstructor;
import org.koreait.board.entities.Board;
import org.koreait.global.search.CommonSearch;
import org.koreait.global.search.ListData;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {

    /**
     * 게시판 설정 한개 조회
     *
     * @param bid
     * @return
     */
    public Board get(String bid) {

        return null;
    }

    /**
     * 게시판 목록 조회
     *
     * @param search
     * @return
     */
    public ListData<Board> getList(CommonSearch search) {

        return null;
    }
}
