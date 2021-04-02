package com.dataart.cerebro.dto;

import javassist.NotFoundException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum StatusDTO {
    ACTIVE(1, "active"),
    SOLD(2, "sold"),
    CLOSED(3, "closed");

    private final Integer id;
    private final String name;
    private static final Map<Integer, StatusDTO> map = new HashMap<>();

    StatusDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    static {
        for (StatusDTO statusDTO : StatusDTO.values()) {
            map.put(statusDTO.id, statusDTO);
        }
    }

    public static StatusDTO getStatusDTOById(int id) throws NotFoundException {
        if (!map.containsKey(id)) {
            throw new NotFoundException(String.format("Type with %d doesn't exists", id));
        }
        return map.get(id);
    }
}
