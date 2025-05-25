package org.koreait.member.repositories;

import org.junit.jupiter.api.Test;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"default"})
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository repository;

    @Test
    void test1() {
        Member member = new Member();
        member.setEmail("user01@test.org");
        member.setPassword("12345678");
        member.setName("사용자01");
        member.setTermsAgree(true);
        member.setMobile("01010001000");
        member.setAuthority(Authority.ADMIN);
        repository.save(member);

        Member member2 = repository.findById(member.getSeq()).orElse(null);
        System.out.println(member2);
    }

    @Test
    void test2() {
        Member member = repository.findById(1L).orElse(null);
        System.out.println(member);
    }
}
