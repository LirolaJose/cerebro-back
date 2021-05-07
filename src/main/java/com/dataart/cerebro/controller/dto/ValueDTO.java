package com.dataart.cerebro.controller.dto;

import lombok.Data;

@Data
public class ValueDTO {
    private Object value;

    // FIXME: 5/7/2021 replace with allargsconstructor?
    public ValueDTO(Object value){
        this.value = value;
    }
}
