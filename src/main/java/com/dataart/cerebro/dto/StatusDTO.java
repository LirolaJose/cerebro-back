package com.dataart.cerebro.dto;

import lombok.Getter;

@Getter
public enum StatusDTO {
    active(1, "active"),
    sold(2, "sold");

    private Integer id;
    private String name;

    StatusDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
