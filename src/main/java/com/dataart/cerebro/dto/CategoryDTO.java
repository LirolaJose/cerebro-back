package com.dataart.cerebro.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CategoryDTO {
    private Integer id;
    private String name;
    private Set<ServiceDTO> servicesSet;
}
