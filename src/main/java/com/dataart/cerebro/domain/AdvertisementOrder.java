package com.dataart.cerebro.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
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
    @JoinColumn (name = "seller_id")
    private UserInfo seller;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "services_of_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<Service> services;
}
