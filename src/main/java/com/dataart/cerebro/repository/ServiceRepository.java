package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
