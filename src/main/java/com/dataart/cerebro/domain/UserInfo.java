package com.dataart.cerebro.domain;

import com.dataart.cerebro.domain.converter.RoleConverter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 100)
    private String firstName;
    @NotEmpty
    @Size(max = 100)
    private String secondName;


    @Size(min = 9, max = 20)
    private String phone;

    @NotEmpty
    @Size(max = 100)
    @Email(message = "Wrong email")
    private String email;
    private String password;

    private Double moneyAmount;

    @Column(name = "role_id")
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_favourites_advertisements",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "advertisement_id"))
    private Set<Advertisement> favouritesAdvertisements;
}
