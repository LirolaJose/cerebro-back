package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
