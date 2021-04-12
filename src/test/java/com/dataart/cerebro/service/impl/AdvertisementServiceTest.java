package com.dataart.cerebro.service.impl;

import com.dataart.cerebro.domain.AdvertisementDTO;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.service.AdvertisementService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdvertisementServiceTest {

    @Test
    void whenGetAllAdsThenAllAdsIsActive() {
        //given
        AdvertisementService advertisementServiceMock = mock(AdvertisementService.class);
        List<AdvertisementDTO> list = new ArrayList<>();

        AdvertisementDTO ad1 = new AdvertisementDTO();
        AdvertisementDTO ad2 = new AdvertisementDTO();
        ad1.setStatus(Status.ACTIVE);
        ad2.setStatus(Status.ACTIVE);
        list.add(ad1);
        list.add(ad2);

        when(advertisementServiceMock.getAllActiveAdvertisements()).thenReturn(list);

        //when
        List<AdvertisementDTO> advertisementDTOList = advertisementServiceMock.getAllActiveAdvertisements();

        //then
        advertisementDTOList.forEach(advertisementDTO -> assertEquals(Status.ACTIVE, advertisementDTO.getStatus()));
    }
}