package com.dataart.cerebro.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image_bytes")
    private byte[] imageBytes;

    @Column(name = "main_image")
    private Boolean mainImage;

    @ManyToOne
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;
}
