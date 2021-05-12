package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.AdditionalService;
import com.dataart.cerebro.repository.AdditionalServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AdditionalServiceService {
    private final AdditionalServiceRepository additionalServiceRepository;

    public AdditionalServiceService(AdditionalServiceRepository additionalServiceRepository) {
        this.additionalServiceRepository = additionalServiceRepository;
    }

    public List<AdditionalService> findAdditionalServicesByCategory(Long categoryId) {
        return additionalServiceRepository.findAdditionalServiceByCategoryId(categoryId);
    }

    public List<AdditionalService> findAdditionalServiceByAdvertisement(Long advertisementId) {
        return additionalServiceRepository.findAdditionalServicesByAdvertisementId(advertisementId);
    }

    public Double getAdditionalServicesTotalPrice(Set<Long> additionalServicesId) {
        if (additionalServicesId.isEmpty()) {
            log.info("The order without additional services");
            return 0.0;
        }

        List<AdditionalService> additionalServices = additionalServiceRepository.findAllById(additionalServicesId);
        return additionalServiceRepository.getAdditionalServicesPriceSum(additionalServices);
    }
}
