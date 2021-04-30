package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.Image;
import com.dataart.cerebro.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/{advertisementId}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})

    public ResponseEntity<byte[]> getMainImageByAdvertisement(@PathVariable Long advertisementId) throws IOException {
        byte[] imageBytes = imageService.findImageByAdvertisementId(advertisementId);
        return new ResponseEntity<>(imageBytes,HttpStatus.OK);
    }

    @GetMapping(value = "/imagesList/{advertisementId}")

    public ResponseEntity<?> getImagesList(@PathVariable Long advertisementId) {
        List<Image> images = imageService.findAllByAdvertisementId(advertisementId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

}
