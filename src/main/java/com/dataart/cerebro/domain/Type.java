package com.dataart.cerebro.domain;

import com.dataart.cerebro.exception.NotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Type {
    BUY(1L, "buy"),
    SALE(2L, "sale"),
    WORK(4L, "work");

    private final Long id;
    private final String name;
    private static final Map<Long, Type> TYPE_MAP = new HashMap<>();

    Type(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    static {
        for (Type type : Type.values()) {
            TYPE_MAP.put(type.id, type);
        }
    }

    public static Type getTypeById(Long id){
        if (!TYPE_MAP.containsKey(id)) {
            throw new NotFoundException("Type", id);
        }
        return TYPE_MAP.get(id);
    }
}
