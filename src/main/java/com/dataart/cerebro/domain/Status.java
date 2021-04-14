package com.dataart.cerebro.domain;

import com.dataart.cerebro.exception.NotFoundException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Status {
    ACTIVE(1L, "active"),
    SOLD(2L, "sold"),
    CLOSED(3L, "closed");

    private final Long id;
    private final String name;
    private static final Map<Long, Status> STATUS_MAP = new HashMap<>();

    Status(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    static {
        for (Status status : Status.values()) {
            STATUS_MAP.put(status.id, status);
        }
    }

    public static Status getStatusById(Long id) {
        if (!STATUS_MAP.containsKey(id)) {
            throw new NotFoundException("Status", id);
        }
        return STATUS_MAP.get(id);
    }
}
