package com.dataart.cerebro.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table (name = "service")
public class AdditionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;
}
