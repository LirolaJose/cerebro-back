package com.dataart.cerebro.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class AdOrderDTO {
    private Integer id;
    private LocalDateTime orderTime;
    private Double totalPrice;
    private AdvertisementDTO advertisement;
    private ContactInfoDTO contactInfo;
    private Set<ServiceDTO> servicesSet;
}
