package com.dataart.cerebro.service.impl;

import com.dataart.cerebro.dao.AdvertisementRepository;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.ContactInfo;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final EmailService emailService;

    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, EmailService emailService) {
        this.advertisementRepository = advertisementRepository;
        this.emailService = emailService;
    }

    @Override
    public List<Advertisement> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }

    @Override
    public List<Advertisement> findActiveAdvertisements() {
        return advertisementRepository.findAdvertisementsByStatus(Status.ACTIVE);
    }


    @Override
    public List<com.dataart.cerebro.domain.Advertisement> getAllActiveAdvertisements() {
        return advertisementRepository.getAllActiveAdvertisements();
    }

    @Override
    public Advertisement getAdvertisementById(Long id) {
        Advertisement advertisement = advertisementRepository.getAdvertisementById(id);
        if (advertisement == null) {
            log.info("Bad request for ID({}), this id doesn't exist", id);
            throw new NotFoundException("Advertisement", id);
        }
        return advertisement;
    }

    @Override
    public void addAdvertisement(com.dataart.cerebro.domain.Advertisement advertisement, ContactInfo contactInfo, byte[] image) {
//        LocalDateTime publicationTime = LocalDateTime.now();
//        LocalDateTime endTime = publicationTime.plusDays(7);
//
//        this.advertisementRepository.addAdvertisement(advertisement.getTitle(), advertisement.getText(), advertisement.getPrice(),
//                image, publicationTime, endTime, advertisement.getCategory(),
//                advertisement.getType(), Status.ACTIVE, contactInfo);
//
//        emailService.sendEmailAboutPublication(advertisement, contactInfo);
    }
}
