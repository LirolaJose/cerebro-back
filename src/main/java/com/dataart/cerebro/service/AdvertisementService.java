package com.dataart.cerebro.service;

import com.dataart.cerebro.repository.AdvertisementRepository;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final EmailService emailService;

    public AdvertisementService(AdvertisementRepository advertisementRepository, EmailService emailService) {
        this.advertisementRepository = advertisementRepository;
        this.emailService = emailService;
    }


    public List<Advertisement> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }


    public List<Advertisement> findActiveAdvertisements() {
        return advertisementRepository.findAdvertisementsByStatus(Status.ACTIVE);
    }

    public Advertisement findAdvertisementById(Long id){
        return advertisementRepository.findAdvertisementById(id);
    }

    public List<Advertisement> findAdvertisementsByUserInfoId(Long id){
        return advertisementRepository.findAdvertisementsByOwnerId(id);
    }
    public boolean deleteAdvertisement(Long id){
        return advertisementRepository.deleteAdvertisementById(id);
    }

    public void createNewAdvertisement(Advertisement advertisement){
//        LocalDateTime publicationTime = LocalDateTime.now();
//        advertisement.setPublicationTime(publicationTime);
//        advertisement.setExpiredTime(publicationTime.plusDays(7));
        advertisementRepository.save(advertisement);
//        emailService.sendEmailAboutPublication(advertisement);
    }
}
