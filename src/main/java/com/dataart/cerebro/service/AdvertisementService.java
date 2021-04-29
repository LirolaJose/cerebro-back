package com.dataart.cerebro.service;

import com.dataart.cerebro.controller.dto.AdvertisementDTO;
import com.dataart.cerebro.domain.*;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.repository.AdvertisementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@Slf4j
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final EmailService emailService;
    private final UserInfoService userInfoService;
    private final CategoryService categoryService;
    private final AdditionalServiceService additionalServiceService;
    private final ImageService imageService;

    public AdvertisementService(AdvertisementRepository advertisementRepository, EmailService emailService, UserInfoService userInfoService, CategoryService categoryService, AdditionalServiceService additionalServiceService, ImageService imageService) {
        this.advertisementRepository = advertisementRepository;
        this.emailService = emailService;
        this.userInfoService = userInfoService;
        this.categoryService = categoryService;
        this.additionalServiceService = additionalServiceService;
        this.imageService = imageService;
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
    public void createNewAdvertisement(AdvertisementDTO advertisementDTO, List<MultipartFile> images) throws IOException {
        log.info("Creating new advertisement");

        UserInfo owner = userInfoService.getCurrentUser();
        Category category = categoryService.findCategoryById(advertisementDTO.getCategoryId());
        LocalDateTime publicationTime = LocalDateTime.now();
        Set<AdditionalService> additionalServices = new HashSet<>();
        advertisementDTO.getAdditionalServicesId().stream()
                .peek(additionalService -> additionalServices.add(additionalServiceService.findAdditionalServiceById(additionalService)))
                .collect(Collectors.toSet());

        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(advertisementDTO.getTitle());
        advertisement.setText(advertisementDTO.getText());
        advertisement.setPrice(advertisementDTO.getPrice());
        advertisement.setStatus(Status.ACTIVE);
        advertisement.setType(advertisementDTO.getType());
        advertisement.setCategory(category);
        advertisement.setAdditionalServices(additionalServices);
        advertisement.setOwner(owner);
        advertisement.setVisible(true);
        advertisement.setPublicationTime(publicationTime);
        advertisement.setExpiredTime(publicationTime.plusDays(7));

        Advertisement newAdvertisement = advertisementRepository.save(advertisement);
        log.info("New advertisement created ({})", newAdvertisement);

        imageService.saveImage(images, newAdvertisement);
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
        }else{ log.info("No matching advertisements found");}
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
        }else{ log.info("No matching advertisements found");}
    }
}
