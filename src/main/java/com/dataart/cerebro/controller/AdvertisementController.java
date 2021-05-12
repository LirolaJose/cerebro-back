package com.dataart.cerebro.controller;

import com.dataart.cerebro.controller.dto.AdvertisementDTO;
import com.dataart.cerebro.controller.dto.NewAdvertisementDTO;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.exception.ValidationException;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.ImageService;
import io.swagger.annotations.Api;
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
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    private final ImageService imageService;

    public AdvertisementController(AdvertisementService advertisementService, ImageService imageService) {
        this.advertisementService = advertisementService;
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<?> getActiveAdvertisements() {
        List<Advertisement> advertisements = advertisementService.findActiveAdvertisements();
        List<AdvertisementDTO> advertisementDTOS = new ArrayList<>();
                advertisements.forEach(advertisement -> advertisementDTOS.add(new AdvertisementDTO(advertisement)));
        return ResponseEntity.ok(advertisementDTOS);
    }

    @GetMapping("/{advertisementId}")
    public ResponseEntity<?> getAdvertisementById(@PathVariable Long advertisementId) {
        Advertisement advertisement = advertisementService.findAdvertisementById(advertisementId);
        AdvertisementDTO advertisementDTO = new AdvertisementDTO(advertisement);
        return ResponseEntity.ok(advertisementDTO);
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<?> getAdvertisementImageByImageId(@PathVariable Long imageId) throws IOException {
        byte[] imageBytes = imageService.findImageById(imageId);
        return new ResponseEntity<>(imageBytes, HttpStatus.OK);
    }


    @Secured("ROLE_USER")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveAdvertisement(@RequestPart("advertisementDTO") @Valid NewAdvertisementDTO newAdvertisementDTO,
                                               BindingResult bindingResult,
                                               @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            throw new ValidationException(fieldErrorList);
        }
        if(images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String contentType = image.getContentType();
                if (contentType != null && !isSupportedContentType(contentType)) {
                    throw new ValidationException("Only PNG or JPG images are allowed");
                }
            }
        }
        advertisementService.createNewAdvertisement(newAdvertisementDTO, images);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}
