package com.dataart.cerebro.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "second_name")
    private String secondName;


    @Size(min = 9, max = 20)
    @NotEmpty
    @Column(name = "phone")
    private String phone;

    @NotEmpty
    @Size(max = 100)
    @Email(message = "Incorrect email")
    @Column(name = "email")
    private String email;

    @NotEmpty
    @Size(min = 10, max = 100)
    private String password;

    @Column(name = "money_amount")
    private Double moneyAmount;

    @Column(name = "role_id")
    private Role role;
}
