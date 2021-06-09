package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
//    List<Advertisement> findAdvertisementsByStatusOrderByPublicationTimeDesc(Status status);
    Page<Advertisement> findAdvertisementsByStatusOrderByPublicationTimeDesc(Status status, Pageable pageable);

    List<Advertisement> findAdvertisementsByOwnerId(Long ownerId);

    @Query(value =
            "SELECT * FROM advertisement " +
            "WHERE status_id = :status and current_date >= date(expired_time) - :days ;",
            nativeQuery = true)
    List<Advertisement> findAdvertisementsByDate(@Param("status") Long statusId, @Param("days") Integer lookbackDays);
}





