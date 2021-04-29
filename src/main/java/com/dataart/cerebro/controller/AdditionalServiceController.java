package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.AdditionalService;
import com.dataart.cerebro.domain.Category;
import com.dataart.cerebro.service.AdditionalServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdditionalServiceController {
    private final AdditionalServiceService additionalServiceService;

    public AdditionalServiceController(AdditionalServiceService additionalServiceService) {
        this.additionalServiceService = additionalServiceService;
    }

    @GetMapping("/additionalServices/category/{categoryId}")
    public ResponseEntity<?> getAdditionalServicesByCategory(@PathVariable Long categoryId) {
        List<AdditionalService> additionalServices = additionalServiceService.findAdditionalServicesByCategory(categoryId);
        return new ResponseEntity<>(additionalServices, HttpStatus.OK);
    }

    @GetMapping("/additionalServices/advertisement/{advertisementId}")
    public ResponseEntity<?> getAdditionalServicesByAdvertisement(@PathVariable Long advertisementId) {
        List<AdditionalService> additionalServices = additionalServiceService.findAdditionalServiceByAdvertisement(advertisementId);
        return new ResponseEntity<>(additionalServices, HttpStatus.OK);
    }
}

