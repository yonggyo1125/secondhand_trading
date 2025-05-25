package org.koreait.file.entities;

import lombok.Data;
import org.koreait.global.entities.BaseMemberEntity;
import org.koreait.member.entities.Member;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Data
@Table("FILE_INFO")
public class FileInfo extends BaseMemberEntity {
    @Id
    private Long seq;
    private String gid;
    private String location;

    @Column("fileName")
    private String fileName;
    private String extension;

    @Column("contentType")
    private String contentType;

    @MappedCollection(idColumn = "seq", keyColumn = "createdBy")
    private Set<Member> members;
}
