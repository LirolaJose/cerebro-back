package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.AdditionalService;
import com.dataart.cerebro.service.AdditionalServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/additionalServices")
public class AdditionalServiceController {
    private final AdditionalServiceService additionalServiceService;

    public AdditionalServiceController(AdditionalServiceService additionalServiceService) {
        this.additionalServiceService = additionalServiceService;
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getAdditionalServicesByCategory(@PathVariable Long categoryId) {
        List<AdditionalService> additionalServices = additionalServiceService.findAdditionalServicesByCategory(categoryId);
        return ResponseEntity.ok(additionalServices);
    }

    @GetMapping("/advertisement/{advertisementId}")
    public ResponseEntity<?> getAdditionalServicesByAdvertisement(@PathVariable Long advertisementId) {
        List<AdditionalService> additionalServices = additionalServiceService.findAdditionalServiceByAdvertisement(advertisementId);
        return ResponseEntity.ok(additionalServices);
    }
}

