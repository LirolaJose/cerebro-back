package com.dataart.cerebro.domain;

import com.dataart.cerebro.exception.NotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Getter
@Slf4j
public enum Type {
    BUY(1, "buy"),
    SALE(2, "sale"),
    SERVICE(3, "service"),
    WORK(4, "work");

    private final Integer id;
    private final String name;
    private static final Map<Integer, Type> TYPE_MAP = new HashMap<>();

    Type(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    static {
        for (Type type : Type.values()) {
            TYPE_MAP.put(type.id, type);
        }
    }

    public static Type getTypeDTOById(int id){
        if (!TYPE_MAP.containsKey(id)) {
            throw new NotFoundException("Type", id);
        }
        return TYPE_MAP.get(id);
    }
}
