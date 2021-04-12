package com.dataart.cerebro.controller;

import com.dataart.cerebro.dao.AdvertisementDAO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageController {
    private final AdvertisementDAO advertisementDAO;

    public ImageController(AdvertisementDAO advertisementDAO) {
        this.advertisementDAO = advertisementDAO;
    }

    @GetMapping(value = "/image",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImage(@RequestParam int id) throws IOException {
        byte[] image = advertisementDAO.getImageByAdvertisementId(id);
        if (image == null || image.length == 0) {
            var path = new ClassPathResource("image/notFound.jpg");
            image = StreamUtils.copyToByteArray(path.getInputStream());
        }
        return ResponseEntity
                .ok()
                .body(image);
    }
}
