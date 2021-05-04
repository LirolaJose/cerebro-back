package com.dataart.cerebro.controller.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserInfoDTO {
    @NotEmpty
    @Size(max = 100)
    private String firstName;

    @NotEmpty
    @Size(max = 100)
    private String secondName;

    @NotEmpty
    @Size(min = 9, max = 20)
    private String phone;

    @NotEmpty
    @Size(max = 100)
    @Email(message = "Incorrect email")
    private String email;

    @NotEmpty
    @Size(max = 100)
    private String password;
}
