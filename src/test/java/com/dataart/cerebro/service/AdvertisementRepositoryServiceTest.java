package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.repository.AdvertisementRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdvertisementRepositoryServiceTest {

    @Test
    void whenGetAllAdsThenAllAdsIsActive() {
        //given
        AdvertisementRepository advertisementRepositoryMock = mock(AdvertisementRepository.class);
        AdvertisementService advertisementService = new AdvertisementService(advertisementRepositoryMock, null, null,
                null, null,null, null, null);
        List<Advertisement> list = new ArrayList<>();

        Advertisement ad1 = new Advertisement();
        Advertisement ad2 = new Advertisement();
        ad1.setStatus(Status.ACTIVE);
        ad2.setStatus(Status.ACTIVE);
        list.add(ad1);
        list.add(ad2);

        when(advertisementRepositoryMock.findAll()).thenReturn(list);

        //when
        List<Advertisement> advertisementList = advertisementService.findActiveAdvertisements();

        //then
        advertisementList.forEach(advertisementDTO -> assertEquals(Status.ACTIVE, advertisementDTO.getStatus()));
    }
}