package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAdvertisementsByStatus(Status status);

    Advertisement findAdvertisementById(Long id);

    List<Advertisement> findAdvertisementsByOwnerId(Long id);

    Advertisement save(Advertisement advertisement);

    @Query(value = "SELECT * FROM advertisement WHERE status_id = 1 and current_date >= date(expired_time) - interval '1 days';",
            nativeQuery = true)
    List<Advertisement> getExpiringAdvertisements();

    @Query(value = "SELECT * FROM advertisement WHERE status_id = 1 and current_date >= date(expired_time);", nativeQuery = true)
    List<Advertisement> getExpiredAdvertisements();
}




