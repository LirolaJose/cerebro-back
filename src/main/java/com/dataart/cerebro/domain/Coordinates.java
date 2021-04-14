package com.dataart.cerebro.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "coordinates")
public class Coordinates implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    private Double latitude;
    private Double longitude;


}
