package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Image;
import com.dataart.cerebro.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(List<MultipartFile> images, Advertisement advertisement) throws IOException {

        for (int i = 0; i < images.size(); i++) {
            Image image = new Image();
            image.setImage(images.get(i).getBytes());
            image.setAdvertisement(advertisement);
            if (i == 0) {
                image.setMainImage(true);
            }
            imageRepository.save(image);
        }
    }
}

