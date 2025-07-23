package org.koreait.board.services.configs;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.board.entities.Board;
import org.koreait.board.exceptions.BoardNotFoundException;
import org.koreait.board.repositories.BoardRepository;
import org.koreait.global.search.CommonSearch;
import org.koreait.global.search.ListData;
import org.koreait.global.search.Pagination;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Lazy
@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {

    private final BoardRepository repository;
    private final HttpServletRequest request;

    /**
     * 게시판 설정 한개 조회
     *
     * @param bid
     * @return
     */
    public Board get(String bid) {
        Board item = repository.findById(bid).orElseThrow(BoardNotFoundException::new);

        addInfo(item); // 추가 정보 공통 처리

        return item;
    }

    /**
     * 게시판 목록 조회
     *
     * @param search
     * @return
     */
    public ListData<Board> getList(CommonSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        String sopt = search.getSopt();
        String skey = search.getSkey();

        BooleanBuilder andBuilder = new BooleanBuilder();

        // 키워드 검색 처리 S

        // 키워드 검색 처리 E

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
        Page<Board> data = repository.findAll(andBuilder, pageable);
        List<Board> items = data.getContent();
        items.forEach(this::addInfo); // 추가정보 처리

        int total = (int)data.getTotalElements();
        Pagination pagination = new Pagination(page, total, 10, limit, request);

        return new ListData<>(items, pagination);
    }

    /**
     * 게시판 설정에 대한 추가 정보 가공 처리
     *
     * @param item
     */
    private void addInfo(Board item) {

    }
}
