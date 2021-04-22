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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

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
        log.info("Find all advertisements");
        return advertisementRepository.findAll();
    }

    public List<Advertisement> findActiveAdvertisements() {
        log.info("Find all ACTIVE advertisements");
        return advertisementRepository.findAdvertisementsByStatus(Status.ACTIVE);
    }

    public Advertisement findAdvertisementById(Long id) {
        log.info("Find advertisement by ID {}", id);
        Advertisement advertisement = advertisementRepository.findAdvertisementById(id);
        if (advertisement == null) {
            log.info("Advertisement with id {} not found", id);
            throw new NotFoundException("Advertisement not found");
        }
        return advertisement;
    }

    public List<Advertisement> findAdvertisementsByUserInfoId(Long id) {
        log.info("Find advertisements by user id {}", id);
        return advertisementRepository.findAdvertisementsByOwnerId(id);
    }

    @Transactional
    public void createNewAdvertisement(Advertisement advertisement) {
        log.info("Creating new advertisement");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo owner = userInfoService.findUserInfoByEmail(authentication.getName());
        advertisement.setOwner(owner);
        LocalDateTime publicationTime = LocalDateTime.now();
        advertisement.setPublicationTime(publicationTime);
        advertisement.setExpiredTime(publicationTime.plusDays(7));
        Advertisement newAdvertisement = advertisementRepository.save(advertisement);
        emailService.sendEmailAboutPublication(newAdvertisement);
    }

    public void findAdvertisementsByExpiringDate(Long statusId, Integer lookbackDays){
        log.info("Searching expiring advertisements");
        List<Advertisement> advertisementsList = advertisementRepository.findAdvertisementsByDate(statusId, lookbackDays);
        Map<String, List<Advertisement>> emailAndAds = advertisementsList.stream()
                .collect(groupingBy(ad -> ad.getOwner().getEmail()));
        if (!emailAndAds.isEmpty()) {
            emailService.sendEmailAboutExpiring(emailAndAds);
            log.info("Letter was sent to {} addresses", emailAndAds.size());
        }
    }
    public void findAdvertisementsByExpiredDate(Long statusId, Integer lookbackDays){
        log.info("Searching expired advertisements");
        List<Advertisement> advertisementsList = advertisementRepository.findAdvertisementsByDate(statusId, lookbackDays);
        Map<String, List<Advertisement>> emailAndAds = advertisementsList.stream()
                .peek(advertisement -> {
                    advertisement.setStatus(Status.CLOSED);
                    advertisementRepository.save(advertisement);
                })
                .collect(groupingBy(ad -> ad.getOwner().getEmail()));
        if (!emailAndAds.isEmpty()) {
            emailService.sendEmailAboutExpired(emailAndAds);
            log.info("Letter was sent to {} addresses", emailAndAds.size());
        }
    }
}
