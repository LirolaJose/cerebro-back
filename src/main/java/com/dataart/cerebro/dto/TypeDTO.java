package com.dataart.cerebro.dto;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum TypeDTO {
    BUY(1, "buy"),
    SALE(2, "sale"),
    SERVICE(3, "service"),
    WORK(4, "work");

    private final Integer id;
    private final String name;
    private static final Map<Integer, TypeDTO> map = new HashMap<>();

    TypeDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    static {
        for(TypeDTO typeDTO : TypeDTO.values()){
            map.put(typeDTO.id, typeDTO);
        }
    }
    public static TypeDTO getTypeDTOById(int id){
        return map.get(id);
    }
}
