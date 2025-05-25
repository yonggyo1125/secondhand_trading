package org.koreait.file.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.file.entities.FileInfo;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Member;
import org.koreait.member.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@SpringBootTest
public class FileInfoRepositoryTest {
    @Autowired
    private FileInfoRepository fileInfoRepository;
    @Autowired
    private MemberRepository memberRepository;
    private FileInfo item;
    private Member member;

    @BeforeEach
    void init() {
        Member member = new Member();
        member.setEmail("user01@test.org");
        member.setPassword("12345678");
        member.setName("사용자01");
        member.setTermsAgree(true);
        member.setMobile("01010001000");
        member.setAuthority(Authority.ADMIN);
        memberRepository.save(member);
        this.member = member;

        FileInfo item = new FileInfo();
        item.setGid(UUID.randomUUID().toString());
        item.setFileName("filename");
        item.setContentType("plain/text");
        item.setCreatedBy(member.getSeq());
        fileInfoRepository.save(item);
        this.item = item;
    }

    @Test
    void test1() {
        FileInfo item2 = fileInfoRepository.findById(item.getSeq()).orElse(null);
        System.out.println(item2);
    }

    @Test
    void test2() {
        Member member2 = memberRepository.findById(member.getSeq()).orElse(null);
        System.out.println(member2);
    }
}
