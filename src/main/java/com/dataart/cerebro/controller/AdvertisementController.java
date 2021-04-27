package com.dataart.cerebro.controller;

import com.dataart.cerebro.controller.dto.AdvertisementDTO;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.service.AdvertisementService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        List<Advertisement> advertisements = advertisementService.findActiveAdvertisements();
        return advertisements != null && !advertisements.isEmpty()
                ? new ResponseEntity<>(advertisements, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdvertisementById(@PathVariable Long id) {
        Advertisement advertisement = advertisementService.findAdvertisementById(id);
        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAdvertisement(@RequestPart("advertisementDTO") AdvertisementDTO advertisementDTO,
                                              @RequestPart(value = "images") List<MultipartFile> images) throws IOException {
        advertisementService.createNewAdvertisement(advertisementDTO, images);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
