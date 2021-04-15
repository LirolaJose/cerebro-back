package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAdvertisementsByStatus(Status status);

    @Query(nativeQuery = true, value = "SELECT 1")
    List<Advertisement> getAllActiveAdvertisements();

    @Query(nativeQuery = true, value = "SELECT 1")
    Advertisement getAdvertisementById(long id);


    @Query(nativeQuery = true, value = "SELECT 1")
    byte[] getImageByAdvertisementId(int id);

    @Query(nativeQuery = true, value = "SELECT 1")
    void addAdvertisement(String title, String text, Double price, byte[] image, LocalDateTime publicationTime, LocalDateTime expiredTime,
                          Category category, Type type, Status status, ContactInfo contactInfo);

    @Query(nativeQuery = true, value = "SELECT 1")
    List<Advertisement> getExpiringAdvertisements();

    @Query(nativeQuery = true, value = "SELECT 1")
    List<Advertisement> getExpiredAdvertisements();

    @Query(nativeQuery = true, value = "SELECT 1")
    void updAdvertisementStatus(Advertisement advertisement, Status status);
}




