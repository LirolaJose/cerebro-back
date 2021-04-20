package com.dataart.cerebro.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "advertisement_order")
public class AdvertisementOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderTime;
    private Double totalPrice;

    @OneToOne
    @JoinColumn (name = "advertisement_id")
    private Advertisement advertisement;

    @OneToOne
    @JoinColumn (name = "customer_id")
    private UserInfo customer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "services_of_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<AdditionalService> additionalServices;
}
