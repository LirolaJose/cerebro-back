package com.dataart.cerebro.controller.dto;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Category;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.domain.Type;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AdvertisementDTO {

    private Long id;
    @NotEmpty
    @Size(max = 100)
    private String title;

    @Size(max = 2000)
    private String text;

    @NotNull
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
