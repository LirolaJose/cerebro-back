package com.dataart.cerebro.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "coordinates")
@ToString(exclude = "advertisement")
public class Coordinates implements Serializable{
    @Id
    @OneToOne
    @JoinColumn(name = "advertisement_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Advertisement advertisement;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
}
