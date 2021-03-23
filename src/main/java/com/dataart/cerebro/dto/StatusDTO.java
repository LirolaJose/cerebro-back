package com.dataart.cerebro.dto;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum StatusDTO {
    ACTIVE(1, "active"),
    SOLD(2, "sold");

    private Integer id;
    private String name;
    private static Map map = new HashMap<>();

    StatusDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
    static {
        for(StatusDTO statusDTO : StatusDTO.values()){
            map.put(statusDTO.id, statusDTO);
        }
    }
    public static StatusDTO getStatusDTOById(int id){
        return (StatusDTO) map.get(id);
    }
}
