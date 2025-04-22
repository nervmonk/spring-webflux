package com.dwikyryan.webflux_playground.sec02.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {
    @Id
    private Integer id;
    private String description;
    private Integer price;
}
