package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.repository.AdvertisementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final EmailService emailService;
    private final UserInfoService userInfoService;

    public AdvertisementService(AdvertisementRepository advertisementRepository, EmailService emailService, UserInfoService userInfoService) {
        this.advertisementRepository = advertisementRepository;
        this.emailService = emailService;
        this.userInfoService = userInfoService;
    }


    public List<Advertisement> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }

    public List<Advertisement> findActiveAdvertisements() {
        return advertisementRepository.findAdvertisementsByStatus(Status.ACTIVE);
    }

    public Advertisement findAdvertisementById(Long id) {
        Advertisement advertisement = advertisementRepository.findAdvertisementById(id);
        if (advertisement == null) {
            log.info("Advertisement with id {} not found", id);
            throw new NotFoundException("Advertisement not found");
        }
        return advertisement;
    }

    public List<Advertisement> findAdvertisementsByUserInfoId(Long id) {
        return advertisementRepository.findAdvertisementsByOwnerId(id);
    }

    public void createNewAdvertisement(Advertisement advertisement) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo owner = userInfoService.findUserInfoByEmail(authentication.getName());
        advertisement.setOwner(owner);
        LocalDateTime publicationTime = LocalDateTime.now();
        advertisement.setPublicationTime(publicationTime);
        advertisement.setExpiredTime(publicationTime.plusDays(7));
        Advertisement newAdvertisement = advertisementRepository.save(advertisement);
//        emailService.sendEmailAboutPublication(newAdvertisement);
        emailService.sendEmail(newAdvertisement);


    }
}
