package com.dataart.cerebro.dto;

import lombok.Data;

import java.awt.*;
import java.time.LocalDateTime;

@Data
public class AdvertisementDTO {
    private int id;
    private String title;
    private String text;
    private double price;
    private Image image;
    private String address;
    private LocalDateTime publicationTime;
    private LocalDateTime endTime;
    private int categoryId;
    private int typeId;
    private int statusId;
    private int ownerId;
}
