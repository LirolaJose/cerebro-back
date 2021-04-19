package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.service.AdvertisementService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisement")
@Api(tags = "Advertisement")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }


    @GetMapping("/")
    public ResponseEntity<?> getActiveAdvertisements() {
        final List<Advertisement> advertisements = advertisementService.findActiveAdvertisements();
        return advertisements != null && !advertisements.isEmpty()
                ? new ResponseEntity<>(advertisements, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdvertisementById(@PathVariable Long id) {
        Advertisement advertisement = advertisementService.findAdvertisementById(id);
        return advertisement != null
                ? new ResponseEntity<>(advertisement, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<?> addAdvertisement(@RequestBody Advertisement advertisement) {
        advertisementService.createNewAdvertisement(advertisement);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
//    @PostMapping("/")
//    public void addAdvertisement(@RequestBody Advertisement advertisement) {
//        advertisementService.createNewAdvertisement(advertisement);
//    }
}
