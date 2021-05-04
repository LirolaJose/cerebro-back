package com.dataart.cerebro.controller.dto;

import com.dataart.cerebro.domain.Type;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.util.Set;

@Data
public class AdvertisementDTO {
    @NotEmpty
    @Size(max = 100)
    private String title;

    @Size(max = 2000)
    private String text;

    @NotNull
    private Double price;

    private Type type;
    private Long categoryId;
    private Set<Long> additionalServicesId;
}
