package com.dataart.cerebro.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserInfoDTO {
    private Long id;
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
    @Size(min = 4, max = 100)
    private String password;

    private Double moneyAmount = 0.0;
}
