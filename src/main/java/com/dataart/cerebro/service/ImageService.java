package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Image;
import com.dataart.cerebro.exception.DataProcessingException;
import com.dataart.cerebro.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public void saveImages(List<MultipartFile> images, Advertisement advertisement) {
        try {
            for (int i = 0; i < images.size(); i++) {
                Image image = new Image();
                image.setImageBytes(images.get(i).getBytes());
                image.setAdvertisement(advertisement);
                if (i == 0) {
                    image.setMainImage(true);
                }
                imageRepository.save(image);
            }
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage(), e);
            throw new DataProcessingException("Error during image saving", e);
        }
    }

    public byte[] findImageByAdvertisementId(Long advertisementId) {
        Image image = imageRepository.findImageByAdvertisement_IdAndMainImageTrue(advertisementId);
        return getImage(image);
    }

    public byte[] findImageById(Long imageId) {
        Image image = imageRepository.findImageById(imageId);
        return getImage(image);
    }

    public List<Image> findAllByAdvertisementId(Long advertisementId) {
        return imageRepository.findAllByAdvertisement_Id(advertisementId);
    }

    private byte[] getImage(Image image) {
        try {
            if (image != null) {
                return image.getImageBytes();
            }
            ClassPathResource path = new ClassPathResource("image/notFound.jpg");
            return StreamUtils.copyToByteArray(path.getInputStream());
        } catch (IOException e) {
            log.error("Error: {}", e.getMessage(), e);
            throw new DataProcessingException("Error during image saving", e);
        }
    }
}

