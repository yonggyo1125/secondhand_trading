package org.koreait.trend.entities;

import lombok.Data;
import org.koreait.global.entities.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
public class Trend extends BaseEntity {
    @Id
    private Long seq;
    private String category;

    @Column("wordCloud")
    private String wordCloud;

    private String keywords;
}
