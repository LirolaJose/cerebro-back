package com.dataart.cerebro.service;

import com.dataart.cerebro.configuration.model_mapper.AdvertisementMapper;
import com.dataart.cerebro.configuration.model_mapper.CoordinateMapper;
import com.dataart.cerebro.controller.dto.CoordinatesDTO;
import com.dataart.cerebro.controller.dto.NewAdvertisementDTO;
import com.dataart.cerebro.domain.*;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.exception.DataProcessingException;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.repository.AdditionalServiceRepository;
import com.dataart.cerebro.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final EmailService emailService;
    private final UserInfoService userInfoService;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final AdditionalServiceRepository additionalServiceRepository;
    private final AdvertisementMapper advertisementMapper;
    private final CoordinateMapper coordinateMapper;

    public List<Advertisement> findActiveAdvertisements() {
        return advertisementRepository.findAdvertisementsByStatusOrderByPublicationTimeDesc(Status.ACTIVE);
    }

    public Advertisement findAdvertisementById(Long advertisementId) {
        return advertisementRepository.findById(advertisementId).orElseThrow(() -> new NotFoundException("Advertisement", advertisementId));
    }

    public List<Advertisement> findAdvertisementsByUserInfoId(Long userInfoId) {
        return advertisementRepository.findAdvertisementsByOwnerId(userInfoId);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void createNewAdvertisement(NewAdvertisementDTO newAdvertisementDTO, List<MultipartFile> images, CoordinatesDTO coordinatesDTO) {
        log.info("Creating new advertisement");

        UserInfo owner = userInfoService.getCurrentUser();
        Category category = categoryService.findCategoryById(newAdvertisementDTO.getCategoryId());
        LocalDateTime publicationTime = LocalDateTime.now();
        Set<AdditionalService> additionalServices = new HashSet<>(additionalServiceRepository.findAllById(newAdvertisementDTO.getAdditionalServicesId()));


        Advertisement advertisement = advertisementMapper.convertToAdvertisement(newAdvertisementDTO);

        // TODO: 08.06.2021 if null
        if (coordinatesDTO != null) {
            Coordinates coordinates = coordinateMapper.convertToCoordinates(coordinatesDTO);
            coordinates.setLatitude(coordinatesDTO.getLatitude());
            coordinates.setLongitude(coordinatesDTO.getLongitude());

            coordinates.setAdvertisement(advertisement);
            advertisement.setCoordinates(coordinates);
        }

        advertisement.setCategory(category);
        advertisement.setAdditionalServices(additionalServices);
        advertisement.setOwner(owner);
        advertisement.setPublicationTime(publicationTime);
        advertisement.setExpiredTime(publicationTime.plusDays(7));

        try {
            Advertisement newAdvertisement = advertisementRepository.save(advertisement);
            if (images != null) {
                imageService.saveImages(images, newAdvertisement);
            }
            log.info("New advertisement created ({})", newAdvertisement);
            emailService.sendEmailAboutPublication(newAdvertisement);
        } catch (Exception e) {
            throw new DataProcessingException("Error during creating the advertisement", e);
        }
    }


    public void findAndNotifyByExpiringInDays(Integer lookbackDays) {
        List<Advertisement> advertisementsList = advertisementRepository.findAdvertisementsByDate(Status.ACTIVE.getId(), lookbackDays);
        Map<String, List<Advertisement>> emailAndAds = advertisementsList.stream()
                .collect(groupingBy(ad -> ad.getOwner().getEmail()));

        if (emailAndAds.isEmpty()) {
            log.info("No matching expiring advertisements found");
        } else {
            emailService.sendEmailAboutFinishingAdvertisement(emailAndAds, "soon");
            log.info("Letter was sent to {} addresses", emailAndAds.size());
        }
    }

    public void findAdvertisementsByExpiredDate() {
        List<Advertisement> advertisementsList = advertisementRepository.findAdvertisementsByDate(Status.ACTIVE.getId(), 0);
        Map<String, List<Advertisement>> emailAndAds = advertisementsList.stream()
                .peek(advertisement -> {
                    advertisement.setStatus(Status.CLOSED);
                    advertisementRepository.save(advertisement);
                })
                .collect(groupingBy(ad -> ad.getOwner().getEmail()));

        if (emailAndAds.isEmpty()) {
            log.info("No matching expired advertisements found");
        } else {
            emailService.sendEmailAboutFinishingAdvertisement(emailAndAds, "expired");
            log.info("Letter was sent to {} addresses", emailAndAds.size());
        }
    }
}
