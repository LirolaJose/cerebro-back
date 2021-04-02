package com.dataart.cerebro.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class AdvertisementDTO {
    private Integer id;
    @NotEmpty
    @Size(max = 100)
    private String title;

    @Size(max = 2000)
    private String text;

    @NotNull
    private Double price;
    private byte[] image;

    @Size(max = 150)
    private String address;

    private LocalDateTime publicationTime;
    private LocalDateTime endTime;
    private CategoryDTO category;
    private Type type;
    private Status status;
    private ContactInfoDTO owner;
}
