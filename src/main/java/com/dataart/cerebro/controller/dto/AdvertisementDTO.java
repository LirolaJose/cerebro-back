package com.dataart.cerebro.controller.dto;

import com.dataart.cerebro.domain.Type;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Set;

@Data
public class AdvertisementDTO {
    private String title;
    private String text;
    private Double price;
    private Type type;
    private Long categoryId;
    private Set<Long> additionalServices;
}
