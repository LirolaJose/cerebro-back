package com.dataart.cerebro.controller;

import com.dataart.cerebro.controller.dto.AdvertisementDTO;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.ImageService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/advertisement")
@Api(tags = "Advertisement")
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    private final ImageService imageService;

    public AdvertisementController(AdvertisementService advertisementService, ImageService imageService) {
        this.advertisementService = advertisementService;
        this.imageService = imageService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getActiveAdvertisements() {
        List<Advertisement> advertisements = advertisementService.findActiveAdvertisements();
        return advertisements != null && !advertisements.isEmpty()
                ? new ResponseEntity<>(advertisements, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{advertisementId}")
    public ResponseEntity<?> getAdvertisementById(@PathVariable Long advertisementId) {
        Advertisement advertisement = advertisementService.findAdvertisementById(advertisementId);
        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<?> getAdvertisementImageByImageId(@PathVariable Long imageId) throws IOException {
        byte[] imageBytes = imageService.findImageById(imageId);
        return new ResponseEntity<>(imageBytes,HttpStatus.OK);
    }


    @Secured("ROLE_USER")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveAdvertisement(@RequestPart("advertisementDTO") AdvertisementDTO advertisementDTO,
                                               @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        advertisementService.createNewAdvertisement(advertisementDTO, images);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
