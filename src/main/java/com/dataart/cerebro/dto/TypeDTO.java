package com.dataart.cerebro.dto;

import lombok.Getter;

@Getter
public enum TypeDTO {
    buy(1, "buy"),
    sale(2, "sale"),
    service(3, "service"),
    work(4, "work");

    private Integer id;
    private String name;

    TypeDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    }
