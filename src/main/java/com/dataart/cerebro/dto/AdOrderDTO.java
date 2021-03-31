package com.dataart.cerebro.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
public class AdOrderDTO {
    private Integer id;
    private LocalDateTime orderTime;
    private Double totalPrice;
    private AdvertisementDTO advertisementDTO;
    private ContactInfoDTO contactInfoDTO;
    private Set<ServiceDTO> servicesSet;
}
