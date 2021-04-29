package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.AdditionalService;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Category;
import com.dataart.cerebro.repository.AdditionalServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public AdditionalService findAdditionalServiceById(Long id) {
        return additionalServiceRepository.findAdditionalServiceById(id);
    }

    public List<AdditionalService> findAdditionalServiceByAdvertisement(Long advertisementId) {
        return additionalServiceRepository.findAdditionalServicesByAdvertisementId(advertisementId);
    }

    public Double getAdditionalServicesTotalPrice(Set<Long> additionalServicesId) {
        if (additionalServicesId.isEmpty()) {
            log.info("The order without additional services");
            return 0.0;
        }
        List<AdditionalService> additionalServices = new ArrayList<>();
        additionalServicesId.forEach(aLong -> additionalServices.add(additionalServiceRepository.findAdditionalServiceById(aLong)));

        return additionalServices.stream().mapToDouble(AdditionalService::getPrice).sum();
    }
}
