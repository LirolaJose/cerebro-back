package com.dataart.cerebro.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdvertisementDTO {
    private Integer id;
    private String title;
    private String text;
    private Double price;
    private byte[] image;
    private String address;
    private LocalDateTime publicationTime;
    private LocalDateTime endTime;
    private CategoryDTO categoryDTO;
    private TypeDTO typeDTO;
    private StatusDTO statusDTO;
    private ContactInfoDTO owner;
}
