package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.service.AdvertisementService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Api(tags = "Admin")
public class AdminController {
    private final AdvertisementService advertisementService;

    public AdminController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping("/allAdvertisements")
    public ResponseEntity<?> getAllAdvertisements(){
        List<Advertisement> advertisements = advertisementService.findAllAdvertisements();
        return advertisements != null && !advertisements.isEmpty()
                ? new ResponseEntity<>(advertisements, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
