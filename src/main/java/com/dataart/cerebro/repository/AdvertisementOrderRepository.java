package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.AdvertisementOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertisementOrderRepository extends JpaRepository<AdvertisementOrder, Long> {
    List<AdvertisementOrder> findAdvertisementOrdersByAdvertisementOwnerId(Long id);
}
