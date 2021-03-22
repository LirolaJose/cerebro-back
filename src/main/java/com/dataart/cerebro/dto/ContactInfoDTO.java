package com.dataart.cerebro.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ContactInfoDTO {
    private Integer id;
    private String name;
    @NotNull
    @Size(min = 9)
    private String phone;

    @NotNull
    @Size(min = 2)
    @Email(message = "Email should be valid")
    private String email;
}
