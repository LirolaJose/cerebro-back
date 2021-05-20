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
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "title")
    private String title;

    @Size(max = 2000)
    @Column(name = "text")
    private String text;

    @NotNull
    @Column(name = "price")
    private Double price;

    @Column(name = "publication_time")
    private LocalDateTime publicationTime;

    @Column(name = "expired_time")
    private LocalDateTime expiredTime;

    @Column(name = "visible")
    private Boolean visible;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "type_id")
    private Type type;

    @Column(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserInfo owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "services_of_advertisement",
            joinColumns = @JoinColumn(name = "advertisement_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<AdditionalService> additionalServices;

    @OneToOne(mappedBy = "advertisement", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Coordinates coordinates;
}
