package com.dwikyryan.webflux_playground.sec08.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Table("product")
public class Product {
    @Id
    private Integer id;
    private String description;
    private Integer price;
}
