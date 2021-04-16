package com.dataart.cerebro.domain;

import com.dataart.cerebro.exception.NotFoundException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Role {
    ADMIN (1L, "ADMIN"),
    USER (2L, "USER");

    private final Long id;
    private final String name;
    private static final Map<Long, Role> ROLE_MAP = new HashMap<>();

    Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    static {
        for (Role role : Role.values()) {
            ROLE_MAP.put(role.id, role);
        }
    }
    public static Role getRoleById(Long id) {
        if (!ROLE_MAP.containsKey(id)) {
            throw new NotFoundException("Status", id);
        }
        return ROLE_MAP.get(id);
    }
}
