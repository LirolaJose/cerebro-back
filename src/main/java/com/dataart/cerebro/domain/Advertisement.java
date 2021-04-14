package com.dataart.cerebro.domain;

import com.dataart.cerebro.domain.converter.StatusConverter;
import com.dataart.cerebro.domain.converter.TypeConverter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Table (name = "advertisement")
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

    @OneToOne
    @JoinColumn (name = "category_id")
    private Category category;

    @Column(name = "type_id")
    private Type type;

    @Column(name = "status_id")
    private Status status;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private UserInfo owner;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "services_of_advertisement",
//            joinColumns = @JoinColumn(name = "advertisement_id"),
//            inverseJoinColumns = @JoinColumn(name = "service_id"))
//    private Set<Service> services;
}
