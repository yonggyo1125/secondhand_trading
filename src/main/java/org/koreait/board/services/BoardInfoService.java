package org.koreait.board.services;

import lombok.RequiredArgsConstructor;
import org.koreait.board.controllers.BoardSearch;
import org.koreait.board.entities.BoardData;
import org.koreait.board.repositories.BoardDataRepository;
import org.koreait.board.services.configs.BoardConfigInfoService;
import org.koreait.file.services.FileInfoService;
import org.koreait.global.search.ListData;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class BoardInfoService {

    private final BoardConfigInfoService configInfoService;
    private final BoardDataRepository boardDataRepository;
    private final FileInfoService fileInfoService;

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
