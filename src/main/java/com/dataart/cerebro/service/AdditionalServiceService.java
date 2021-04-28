package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.AdditionalService;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Category;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.repository.AdditionalServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdditionalServiceService {
    private final AdditionalServiceRepository additionalServiceRepository;

    public AdditionalServiceService(AdditionalServiceRepository additionalServiceRepository) {
        this.additionalServiceRepository = additionalServiceRepository;
    }

    public List<AdditionalService> findAdditionalServicesByCategory(Category category){
        List<AdditionalService> additionalServices = additionalServiceRepository.findAdditionalServiceByCategoryId(category.getId());
        if(additionalServices.isEmpty()){
            log.info("{} doesn't have additional services", category.getName());
            return additionalServices;
//            throw new NotFoundException(category.getName() + " doesn't have additional services");
        }
        return additionalServices;
    }

    public AdditionalService findAdditionalServiceById(Long id){
        return additionalServiceRepository.findAdditionalServiceById(id);
    }

    public List<AdditionalService> findAdditionalServiceByAdvertisement(Advertisement advertisement){
        return additionalServiceRepository.findAdditionalServicesByAdvertisementId(advertisement.getId());
    }
}
