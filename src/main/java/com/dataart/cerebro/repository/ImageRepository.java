package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findImageByAdvertisement_IdAndMainImageTrue(Long imageId);

    Image findImageById(Long imageId);

    List<Image> findAllByAdvertisement_Id(Long advertisementId);
}
