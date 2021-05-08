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
        List<AdditionalService> additionalServices = additionalServiceRepository.findAdditionalServiceByCategoryId(categoryId);
        if (additionalServices.isEmpty()) {
            log.info("Category with {} doesn't have additional services", categoryId);
            return additionalServices;
        }
        return additionalServices;
    }

    public AdditionalService findAdditionalServiceById(Long additionalServiceId) {
        return additionalServiceRepository.findAdditionalServiceById(additionalServiceId);
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
