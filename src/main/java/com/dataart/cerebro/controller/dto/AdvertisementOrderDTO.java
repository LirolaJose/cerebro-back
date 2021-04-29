package com.dataart.cerebro.controller.dto;

import lombok.Data;

import java.util.Set;

@Data
public class AdvertisementOrderDTO {
    private Long advertisementId;
    private Set<Long> additionalServicesId;
}
