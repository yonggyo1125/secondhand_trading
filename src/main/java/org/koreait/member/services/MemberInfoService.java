package org.koreait.member.services;

import lombok.RequiredArgsConstructor;
import org.koreait.global.search.ListData;
import org.koreait.member.MemberInfo;
import org.koreait.member.constants.Authority;
import org.koreait.member.controllers.MemberSearch;
import org.koreait.member.entities.Member;
import org.koreait.member.repositories.MemberRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Lazy
@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository repository;
    private final JdbcTemplate jdbcTemplate;

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
        int offset = (page - 1) * limit; // 레코드 시작 번호


        List<String> addWhere = new ArrayList<>();
        List<Object> params = new ArrayList<>();


        params.add(offset);
        params.add(limit);

        StringBuffer sb = new StringBuffer(2000);
        sb.append("SELECT * FROM MEMBER");

        sb.append("ORDER BY createdAt DESC");
        sb.append("LIMIT ?, ?");

        List<Member> items = jdbcTemplate.query(sb.toString(), this::mapper, params.toArray());

        return null;
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
