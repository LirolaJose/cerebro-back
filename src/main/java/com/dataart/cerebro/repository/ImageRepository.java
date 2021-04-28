package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findImageByAdvertisement_IdAndMainImageTrue(Long id);

    Image findImageById(Long id);

    List<Image> findAllByAdvertisement_Id(@Param("id") Long id);
}
