package org.koreait.global.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.relational.core.mapping.Column;

@Data
public abstract class BaseMemberEntity extends BaseEntity {
    @CreatedBy
    @Column("createdBy")
    private Long createdBy;
    @LastModifiedBy
    @Column("modifiedBy")
    private Long modifiedBy;
}
