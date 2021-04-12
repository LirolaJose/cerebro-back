package com.dataart.cerebro.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ContactInfoDTO {
    private Integer id;
    @NotEmpty
    @Size(max = 100)
    private String name;


    @Size(min = 9, max = 20)
    private String phone;

    @NotEmpty
    @Size(max = 100)
    @Email(message = "Wrong email")
    private String email;
}
