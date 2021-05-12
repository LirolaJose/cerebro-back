package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.Image;
import com.dataart.cerebro.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/{advertisementId}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getMainImageByAdvertisement(@PathVariable Long advertisementId) {
        byte[] imageBytes = imageService.findImageByAdvertisementId(advertisementId);
        return ResponseEntity.ok(imageBytes);
    }

    @GetMapping(value = "/imagesList/{advertisementId}")
    public ResponseEntity<?> getImagesList(@PathVariable Long advertisementId) {
        List<Long> imagesId = imageService.findAllByAdvertisementId(advertisementId).stream()
                .map(Image::getId)
                .collect(Collectors.toList());
        return ResponseEntity.ok(imagesId);
    }
}
