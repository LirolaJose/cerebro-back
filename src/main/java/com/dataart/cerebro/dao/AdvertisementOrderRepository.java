package com.dataart.cerebro.dao;

import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
public interface AdvertisementOrderRepository extends JpaRepository<AdvertisementOrder, Long> {
    @Query(nativeQuery = true, value = "SELECT 1")

    AdvertisementOrder addAdOrder(AdvertisementOrder adOrder, LocalDateTime orderTime, Advertisement advertisement, ContactInfo customerInfo);
}
