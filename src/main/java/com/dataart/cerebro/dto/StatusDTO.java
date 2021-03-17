package com.dataart.cerebro.dto;

import lombok.Getter;

@Getter
public enum StatusDTO {
    ACTIVE(1, "active"),
    SOLD(2, "sold");

    private Integer id;
    private String name;

    StatusDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
