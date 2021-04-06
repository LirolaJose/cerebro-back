package com.dataart.cerebro.domain;

import com.dataart.cerebro.exception.NotFoundException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Status {
    ACTIVE(1, "active"),
    SOLD(2, "sold"),
    CLOSED(3, "closed");

    private final Integer id;
    private final String name;
    private static final Map<Integer, Status> STATUS_MAP = new HashMap<>();

    Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    static {
        for (Status status : Status.values()) {
            STATUS_MAP.put(status.id, status);
        }
    }

    public static Status getStatusDTOById(int id) {
        if (!STATUS_MAP.containsKey(id)) {
            throw new NotFoundException("Status", id);
        }
        return STATUS_MAP.get(id);
    }
}
