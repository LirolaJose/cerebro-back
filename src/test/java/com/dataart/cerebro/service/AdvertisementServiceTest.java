package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.*;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.repository.AdditionalServiceRepository;
import com.dataart.cerebro.repository.AdvertisementRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
class AdvertisementServiceTest {

    @Autowired
    CategoryService categoryService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    AdvertisementRepository advertisementRepository;
    @Autowired
    AdditionalServiceRepository additionalServiceRepository;

    @Test
    void whenAdvertisementIsAddedThenCoordinatesIsAddedToo() {
        Advertisement advertisement = new Advertisement();
        LocalDateTime publicationTime = LocalDateTime.now();
        Category category = categoryService.findCategoryById(1L);
        UserInfo userInfo = userInfoService.findUserInfoById(3L);
        advertisement.setTitle("Ad Coordinates");
        advertisement.setPrice(100.0);
        advertisement.setStatus(Status.ACTIVE);
        advertisement.setType(Type.SELL);
        advertisement.setCategory(category);
        advertisement.setOwner(userInfo);
        advertisement.setVisible(true);
        advertisement.setPublicationTime(publicationTime);
        advertisement.setExpiredTime(publicationTime.plusDays(7));


        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(50.45);
        coordinates.setLongitude(50.45);
        coordinates.setAdvertisement(advertisement);

        advertisement.setCoordinates(coordinates);

        Advertisement newAdvertisement = advertisementRepository.save(advertisement);

        assertThat(newAdvertisement.getCoordinates().getLatitude(), equalTo(coordinates.getLatitude()));
    }

    @Test
    void whenEditAdvertisementThenCoordinatesIsEdited() {
        Advertisement advertisement = advertisementRepository.findById(1L).orElseThrow(() -> new NotFoundException("Advertisement", 1L));
        Coordinates coordinates = advertisement.getCoordinates();
        coordinates.setLongitude(3.0);
        coordinates.setLatitude(3.0);
        advertisement.setCoordinates(coordinates);
        advertisementRepository.save(advertisement);
    }

    @Test
    void whenFindAdvertisementByIdThenGetAdWithCoordinates() {
        Advertisement advertisement = advertisementRepository.findById(1L).orElseThrow(() -> new NotFoundException("Advertisement", 1L));
        log.info("{}, {}", advertisement.getCoordinates().getLatitude(), advertisement.getCoordinates().getLongitude());
    }

    @Test
    void whenTryToGetAdsAdditionalServicesThenGetLazyInitException() {
        Advertisement advertisement = advertisementRepository.findById(1L).orElseThrow(() -> new NotFoundException("Advertisement", 1L));
        Set<AdditionalService> additionalServices = advertisement.getAdditionalServices();
        assertThrows(LazyInitializationException.class, additionalServices::size);
    }

    @Test
    @Transactional
    void whenTryToGetAdsAdditionalServicesThenGetAdditionalServicesList() {
        Advertisement advertisement = advertisementRepository.findById(1L).orElseThrow(() -> new NotFoundException("Advertisement", 1L));
        Set<AdditionalService> additionalServices = advertisement.getAdditionalServices();
        log.info("{}", additionalServices.size());
        assertEquals(additionalServices.size(), 0);
    }
}
