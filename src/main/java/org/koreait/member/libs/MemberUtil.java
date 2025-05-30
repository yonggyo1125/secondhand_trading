package org.koreait.member.libs;

import org.koreait.member.MemberInfo;
import org.koreait.member.entities.Member;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class MemberUtil {
    // 로그인 여부
    public boolean isLogin() {
        return getMember() != null;
    }

    // 로그인한 회원 정보
    public Member getMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof MemberInfo memberInfo) {
            return memberInfo.getMember();
        }

        return null;
    }
}
