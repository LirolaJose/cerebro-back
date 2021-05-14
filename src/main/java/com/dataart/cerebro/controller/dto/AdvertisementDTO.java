package com.dataart.cerebro.controller.dto;

import com.dataart.cerebro.domain.Advertisement;
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

    public AdvertisementDTO(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.title = advertisement.getTitle();
        this.text = advertisement.getText();
        this.price = advertisement.getPrice();
        this.type = advertisement.getType();
        this.category = advertisement.getCategory();
        this.status = advertisement.getStatus();
        this.owner = new UserInfoDTO(advertisement.getOwner());
    }
}
