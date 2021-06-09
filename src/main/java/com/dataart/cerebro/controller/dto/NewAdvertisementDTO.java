package com.dataart.cerebro.controller.dto;

import com.dataart.cerebro.domain.Type;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class NewAdvertisementDTO {
    @NotEmpty
    @Size(max = 100)
    private String title;

    @Size(max = 2000)
    private String text;

    @NotNull
    private Double price;

    @NotNull
    private Type type;
    @NotNull
    private Long categoryId;
    private Set<Long> additionalServicesId;
}
