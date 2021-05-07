package com.dataart.cerebro.controller;

import com.dataart.cerebro.controller.dto.AdvertisementDTO;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.exception.ValidationException;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.ImageService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/advertisement")
@Api(tags = "Advertisement")
@Slf4j
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
        // FIXME: 5/5/2021 always return ok even if it empty
        return advertisements != null && !advertisements.isEmpty()
                ? new ResponseEntity<>(advertisements, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{advertisementId}")
    public ResponseEntity<?> getAdvertisementById(@PathVariable Long advertisementId) {
        Advertisement advertisement = advertisementService.findAdvertisementById(advertisementId);
        // FIXME: 5/5/2021 return dto everywhere
        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<?> getAdvertisementImageByImageId(@PathVariable Long imageId) throws IOException {
        byte[] imageBytes = imageService.findImageById(imageId);
        return new ResponseEntity<>(imageBytes, HttpStatus.OK);
    }


    @Secured("ROLE_USER")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveAdvertisement(@RequestPart("advertisementDTO") @Valid AdvertisementDTO advertisementDTO,
                                               BindingResult bindingResult,
                                               @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrorList = new ArrayList<>(bindingResult.getFieldErrors());
            // FIXME: 5/5/2021 remove double logging
            log.warn("Some parameters are not filled");
            throw new ValidationException(fieldErrorList);
        }
        // FIXME: 5/7/2021 add backend images size and type check
        advertisementService.createNewAdvertisement(advertisementDTO, images);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
