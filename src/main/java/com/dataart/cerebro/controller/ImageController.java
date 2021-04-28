package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Image;
import com.dataart.cerebro.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/image/{advertisement}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})

    public ResponseEntity<byte[]> getMainImageByAdvertisement(@PathVariable Advertisement advertisement) throws IOException {
        byte[] imageBytes = imageService.findImageByAdvertisement(advertisement);
        return ResponseEntity
                .ok()
                .body(imageBytes);
    }

    @GetMapping(value = "/imageIM/{id}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})

    public ResponseEntity<byte[]> getMainImageById(@PathVariable Long id) {
        byte[] imageBytes = imageService.findImageById(id);
        return ResponseEntity
                .ok()
                .body(imageBytes);
    }

    @GetMapping(value = "/imagesList/{advertisement}")

    public ResponseEntity<?> getImagesList(@PathVariable Advertisement advertisement) {
        List<Image> images = imageService.findAllByAdvertisement(advertisement);
//        List<byte[]> bytesImages = images.stream()
//                .map(Image::getImageBytes)
//                .collect(Collectors.toList());

        return new ResponseEntity<>(images, HttpStatus.OK);
    }

}
