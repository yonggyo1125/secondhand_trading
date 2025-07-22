package org.koreait.board.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;
import org.koreait.member.entities.Member;

@Data
@Entity
public class BoardData extends BaseEntity {
    @Id
    @GeneratedValue
    private Long seq;

    @Column(length=45, nullable = false)
    private String gid;

    @JoinColumn(name="bid")
    @ManyToOne(fetch= FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(length=60)
    private String category; // 게시글 분류

    @Column(length=60, nullable = false)
    private String poster; // 작성자

    @Column(length=65)
    private String guestPw; // 비회원 게시글 수정, 삭제를 위한 비밀번호

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

    private boolean notice; // 공지글 여부
    private boolean secret; // 비밀글 여부

    private int viewCount; // 조회수

    @Column(length=20)
    private String ip; // 작성자 IP 주소

    private String ua; // User-Agent 정보, 작성자의 브라우저 정보
}
