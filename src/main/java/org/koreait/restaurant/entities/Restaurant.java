package org.koreait.restaurant.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
public class Restaurant {
    @Id
    private Long seq;
    private String zipcode;
    private String address;
    private String zonecode;

    @Column("roadAddress")
    private String roadAddress;
    private String category;
    private double lat;
    private double lon;
}
