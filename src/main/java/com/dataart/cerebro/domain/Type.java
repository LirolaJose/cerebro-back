package com.dataart.cerebro.domain;

import com.dataart.cerebro.exception.NotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Type {
    BUY(1L, "BUY", false),
    SELL(2L, "SELL", true),
    WORK(4L, "WORK", false);

    private final Long id;
    private final String name;
    private final Boolean orderable;
    private static final Map<Long, Type> TYPE_MAP = new HashMap<>();

    Type(Long id, String name, Boolean orderable) {
        this.id = id;
        this.name = name;
        this.orderable = orderable;
    }

    static {
        for (Type type : Type.values()) {
            TYPE_MAP.put(type.id, type);
        }
    }

    public static Type getTypeById(Long id) {
        if (!TYPE_MAP.containsKey(id)) {
            throw new NotFoundException("Type", id);
        }
        return TYPE_MAP.get(id);
    }

    public static Type findByName(String name) {
        return Stream.of(values())
                .filter(t -> t.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    @JsonCreator
    public static Type forValues(String typeMap) {

        return findByName(typeMap);

    }
}
