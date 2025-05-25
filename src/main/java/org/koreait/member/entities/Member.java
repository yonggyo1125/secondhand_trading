package org.koreait.member.entities;

import lombok.Data;
import org.koreait.global.entities.BaseEntity;
import org.koreait.member.constants.Authority;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("MEMBER")
public class Member extends BaseEntity {
    @Id
    @Column("seq")
    private Long seq;
    @Column("email")
    private String email;
    @Column("password")
    private String password;
    @Column("name")
    private String name;
    @Column("mobile")
    private String mobile;

    @Column("authority")
    private Authority authority = Authority.MEMBER;

    @Column("termsAgree")
    private boolean termsAgree;
}
