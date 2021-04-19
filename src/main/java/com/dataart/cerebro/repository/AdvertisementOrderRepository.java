package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.AdvertisementOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertisementOrderRepository extends JpaRepository<AdvertisementOrder, Long> {
//    @Query(value = "SELECT * FROM advertisement_order aor join advertisement a on aor.advertisement_id = a.id\n" +
//            "    join user_info ui on a.owner_id = ui.id WHERE a.owner_id = ?1",
//    nativeQuery = true)
//    List<AdvertisementOrder> findAdvertisementOrdersByAdvertisementOwnerId(Long id);

}
