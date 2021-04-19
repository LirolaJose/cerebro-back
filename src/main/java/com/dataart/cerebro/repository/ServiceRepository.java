package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<AdditionalService, Long> {
}
