package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAdvertisementsByStatus(Status status);

    Advertisement findAdvertisementById(Long id);

    List<Advertisement> findAdvertisementsByOwnerId(Long id);

    Boolean deleteAdvertisementById(Long id);
}




