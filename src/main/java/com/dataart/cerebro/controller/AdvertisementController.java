package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.service.AdvertisementService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cerebro/api")
@Api(tags = "Advertisements")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping("/GET/advertisements")
    public List<Advertisement> getActiveAdvertisements() {
        return advertisementService.findActiveAdvertisements();
    }

    @GetMapping("GET/advertisement/{id}")
    public Advertisement getAdvertisementById(@PathVariable Long id) {
        return advertisementService.findAdvertisementById(id);
    }

    @PostMapping("/POST/creation_advertisement")
    public ResponseEntity<Advertisement> addAdvertisement(@RequestBody Advertisement advertisement) {
        advertisementService.createNewAdvertisement(advertisement);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/DELETE/advertisement/{id}")
    public ResponseEntity<Advertisement> deleteAdvertisement(@PathVariable Long id){
        final boolean deleted = advertisementService.deleteAdvertisement(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
