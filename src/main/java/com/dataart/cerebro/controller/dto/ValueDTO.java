package com.dataart.cerebro.controller.dto;

import lombok.Data;

@Data
public class ValueDTO {
    private Object value;

    public ValueDTO(Object value){
        this.value = value;
    }
}
