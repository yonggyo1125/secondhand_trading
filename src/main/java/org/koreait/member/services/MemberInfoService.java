package org.koreait.member.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.global.search.ListData;
import org.koreait.global.search.Pagination;
import org.koreait.member.MemberInfo;
import org.koreait.member.constants.Authority;
import org.koreait.member.controllers.MemberSearch;
import org.koreait.member.entities.Member;
import org.koreait.member.entities.QMember;
import org.koreait.member.repositories.MemberRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Lazy
@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository repository;
    private final HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        Authority authority = Objects.requireNonNullElse(member.getAuthority(), Authority.MEMBER);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority.name()));

        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }

    /**
     * 회원 목록
     *
     * @param search
     * @return
     */
    public ListData<Member> getList(MemberSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        QMember member = QMember.member;
        BooleanBuilder andBuilder = new BooleanBuilder();
        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt.toUpperCase() : "ALL";
        /**
         * 키워드 검색
         * sopt: 검색 옵션
         *      NAME : 회원명
         *      EMAIL : 이메일
         *      MOBILE : 휴대전화번호
         *      ALL : 통합 검색 - NAME + EMAIL + MOBILE
         */
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();
            StringExpression fields = null;
            if (sopt.equals("NAME")) {
                fields = member.name;
            } else if (sopt.equals("EMAIL")) {
                fields = member.email;
            } else if (sopt.equals("MOBILE")) {
                fields = member.mobile;
            } else {
                fields = member.name.concat(member.email)
                        .concat(member.mobile);
            }
        }

//        int offset = (page - 1) * limit; // 레코드 시작 번호
//
//
//        List<String> addWhere = new ArrayList<>();
//        List<Object> params = new ArrayList<>();
//
//        String sopt = search.getSopt();
//        String skey = search.getSkey();
//        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";
//        /**
//         * 키워드 검색
//         * sopt: 검색 옵션
//         *      NAME : 회원명
//         *      EMAIL : 이메일
//         *      MOBILE : 휴대전화번호
//         *      ALL : 통합 검색 - NAME + EMAIL + MOBILE
//         */
//        if (StringUtils.hasText(skey)) { // 검색 키워드가 있는 경우
//            if (sopt.equalsIgnoreCase("NAME")) { // 회원명 검색
//                addWhere.add("name LIKE ?");
//            } else if (sopt.equalsIgnoreCase("EMAIL")) { // 이메일 주소 검색
//                addWhere.add("email LIKE ?");
//            } else if (sopt.equalsIgnoreCase("MOBILE")) { // 휴대전화번호 검색
//                addWhere.add("mobile LIKE ?");
//            } else { // 통합 검색
//                addWhere.add("CONCAT(name, email, mobile) LIKE ?");
//            }
//
//            params.add("%" + skey + "%");
//        }
//
//        // 권한 조건 검색 S
//        List<Authority> authorities = search.getAuthority();
//        if (authorities != null && !authorities.isEmpty()) {
//
//            addWhere.add(" authority IN (" + Stream.generate(() -> "?").limit(authorities.size()).collect(Collectors.joining(",")) + ")");
//
//            authorities.forEach(authority ->  params.add(authority.name()));
//
//        }
//        // 권한 조건 검색 E
//
//        StringBuffer sb = new StringBuffer(2000);
//        StringBuffer sb2 = new StringBuffer(2000);
//        sb.append("SELECT * FROM MEMBER");
//        sb2.append("SELECT COUNT(*) FROM MEMBER");
//
//        if (!addWhere.isEmpty()) {
//            String where = " WHERE " + String.join(" AND ", addWhere);
//            sb.append(where);
//            sb2.append(where);
//        }
//
//        sb.append(" ORDER BY createdAt DESC");
//        sb.append(" LIMIT ?, ?");
//
//
//        int total = jdbcTemplate.queryForObject(sb2.toString(), int.class, params.toArray()); // 검색 조건에 다른 전체 레코드 갯수
//
//        params.add(offset);
//        params.add(limit);
//
//        List<Member> items = jdbcTemplate.query(sb.toString(), this::mapper, params.toArray());



        Pagination pagination = new Pagination(page, total, 10, 20, request);


        return new ListData<>(items, pagination);
    }

    private Member mapper(ResultSet rs, int i) throws SQLException {
        Member item = new Member();
        item.setSeq(rs.getLong("seq"));
        item.setName(rs.getString("name"));
        item.setEmail(rs.getString("email"));
        item.setMobile(rs.getString("mobile"));
        item.setAuthority(Authority.valueOf(rs.getString("authority")));
        item.setLocked(rs.getBoolean("locked"));
        Timestamp expired = rs.getTimestamp("expired");
        Timestamp credentialChangedAt = rs.getTimestamp("credentialChangedAt");
        Timestamp createdAt = rs.getTimestamp("createdAt");
        Timestamp modifiedAt = rs.getTimestamp("modifiedAt");
        Timestamp deletedAt = rs.getTimestamp("deletedAt");

        item.setExpired(expired == null ? null : expired.toLocalDateTime());
        item.setCredentialChangedAt(credentialChangedAt == null ? null : credentialChangedAt.toLocalDateTime());
        item.setCreatedAt(createdAt == null ? null : createdAt.toLocalDateTime());
        item.setModifiedAt(modifiedAt == null ? null : modifiedAt.toLocalDateTime());
        item.setDeletedAt(deletedAt == null ? null : deletedAt.toLocalDateTime());

        return item;
    }
}
