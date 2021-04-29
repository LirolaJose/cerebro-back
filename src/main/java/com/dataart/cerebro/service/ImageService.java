package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Image;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
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

    public void saveImage(List<MultipartFile> images, Advertisement advertisement) throws IOException {

        for (int i = 0; i < images.size(); i++) {
            Image image = new Image();
            image.setImageBytes(images.get(i).getBytes());
            image.setAdvertisement(advertisement);
            if (i == 0) {
                image.setMainImage(true);
            }
            imageRepository.save(image);
        }
    }

    public byte[] findImageByAdvertisement(Long advertisement) throws IOException {
        Image image = imageRepository.findImageByAdvertisement_IdAndMainImageTrue(advertisement);
        return getImage(image);
    }

    public byte[] findImageById(Long id) throws IOException {
        Image image = imageRepository.findImageById(id);
        return getImage(image);
    }

    public List<Image> findAllByAdvertisement(Advertisement advertisement){
        return imageRepository.findAllByAdvertisement_Id(advertisement.getId());
    }

    private byte[] getImage(Image image) throws IOException {
        byte[] imageBytes;
        if (image != null) {
            imageBytes = image.getImageBytes();
        } else {
            var path = new ClassPathResource("image/notFound.jpg");
            imageBytes = StreamUtils.copyToByteArray(path.getInputStream());
        }
        return imageBytes;
    }
}

