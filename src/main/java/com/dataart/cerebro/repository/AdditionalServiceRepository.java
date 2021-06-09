package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long> {
    @Query(value =
            "SELECT s.* FROM service s " +
            "join services_of_category soc on s.id = soc.service_id " +
            "join category c on c.id = soc.category_id " +
            "WHERE c.id = :categoryId ;",
            nativeQuery = true)
    List<AdditionalService> findAdditionalServiceByCategoryId(@Param("categoryId") Long categoryId);

    @Query(value =
            "SELECT s.* FROM service s " +
            "join services_of_advertisement soa on s.id = soa.service_id " +
            "WHERE advertisement_id = :advertisementId ;",
            nativeQuery = true)
    List<AdditionalService> findAdditionalServicesByAdvertisementId(@Param("advertisementId") Long advertisementId);

    @Query("SELECT SUM(price) FROM AdditionalService " +
            "WHERE id IN :services")
    Double getAdditionalServicesPriceSum(@Param("services") Set<Long> additionalServices);
}
