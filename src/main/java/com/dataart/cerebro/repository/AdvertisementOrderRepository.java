package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AdvertisementOrderRepository extends JpaRepository<AdvertisementOrder, Long> {
    List<AdvertisementOrder> findAdvertisementOrdersByAdvertisement_Owner_Id(Long id);

    AdvertisementOrder findAdvertisementOrdersById(Long id);




    @Query(nativeQuery = true, value = "SELECT 1")
    AdvertisementOrder addAdOrder(AdvertisementOrder adOrder, LocalDateTime orderTime, Advertisement advertisement, ContactInfo customerInfo);
}
