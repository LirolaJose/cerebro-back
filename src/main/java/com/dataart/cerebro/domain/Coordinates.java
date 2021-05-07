package com.dataart.cerebro.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "coordinates")
public class Coordinates implements Serializable{
    @Id
    @OneToOne
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;


}
