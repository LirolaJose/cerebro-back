package com.dataart.cerebro.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 100)
    private String title;

    @Size(max = 2000)
    private String text;

    @NotNull
    private Double price;

    private LocalDateTime publicationTime;
    private LocalDateTime expiredTime;
    private Boolean visible;

    // FIXME: 5/5/2021 actually it's  many to one
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "type_id")
    private Type type;

    @Column(name = "status_id")
    private Status status;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private UserInfo owner;

    // FIXME: 5/5/2021 do we need eager?
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "services_of_advertisement",
            joinColumns = @JoinColumn(name = "advertisement_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<AdditionalService> additionalServices;
}
