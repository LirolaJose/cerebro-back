package com.dataart.cerebro.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean mainImage;

    @OneToOne
    @JoinColumn (name = "advertisement_id")
    private Advertisement advertisement;
}
