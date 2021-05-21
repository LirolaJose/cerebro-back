package com.dataart.cerebro.controller.dto;

import com.dataart.cerebro.domain.Category;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.domain.Type;
import lombok.Data;

@Data
public class AdvertisementDTO {

    private Long id;
    private String title;
    private String text;
    private Double price;
    private Type type;
    private Category category;
    private Status status;
    private UserInfoDTO owner;
}
