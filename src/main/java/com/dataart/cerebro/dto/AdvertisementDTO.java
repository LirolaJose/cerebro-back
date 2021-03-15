package com.dataart.cerebro.dto;

import lombok.Data;

import java.awt.*;
import java.util.Date;

@Data
public class AdvertisementDTO {
    private int id;
    private String title;
    private String text;
    private double price;
    private Image image;
    private String address;
    private Date publicationTime;
    private Date endTime;
    private CategoryDTO categoryDTO;
    private int typeId;
    private int statusId;
    private int ownerId;
}
