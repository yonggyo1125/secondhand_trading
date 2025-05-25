package org.koreait.member.libs;

import org.koreait.member.MemberInfo;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MemberUtil {
    public boolean isLogin() {
        return getMember() != null;
    }

    /**
     * 관리자 여부
     *  권한 - MEMBER, ADMIN
     * @return
     */
    public boolean isAdmin() {
        Authority authority = Objects.requireNonNullElse(getMember().getAuthority(), Authority.MEMBER);

        return isLogin() && authority == Authority.ADMIN;
    }

    /**
     * 로그인 한 회원의 정보 조회
     *
     * @return
     */
    public Member getMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof MemberInfo memberInfo) {
            return memberInfo.getMember();
        }

        return null;
    }
}
