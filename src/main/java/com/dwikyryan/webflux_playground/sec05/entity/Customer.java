package com.dwikyryan.webflux_playground.sec05.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Table("customer")
public class Customer {
    @Id
    private Integer id;
    private String name;
    private String email;
}
