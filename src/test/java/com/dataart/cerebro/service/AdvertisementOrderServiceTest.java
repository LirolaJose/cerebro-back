package com.dataart.cerebro.service;

import com.dataart.cerebro.controller.dto.AdvertisementOrderDTO;
import com.dataart.cerebro.domain.*;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.repository.AdditionalServiceRepository;
import com.dataart.cerebro.repository.AdvertisementOrderRepository;
import com.dataart.cerebro.repository.AdvertisementRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AdvertisementOrderServiceTest {

    @Test
    void whenOrderIsAddedThenEmailsAreSend() {
        //given
        AdvertisementOrderRepository advertisementOrderRepositoryMock = mock(AdvertisementOrderRepository.class);
        EmailService emailServiceMock = mock(EmailService.class);
        UserInfo customerInfoMock = mock(UserInfo.class);
        UserInfoService userInfoServiceMock = mock(UserInfoService.class);
        AdvertisementOrderDTO advertisementDTOMock = mock(AdvertisementOrderDTO.class);
        AdvertisementRepository advertisementRepositoryMock = mock(AdvertisementRepository.class);
        AdditionalServiceService additionalServiceServiceMock = mock(AdditionalServiceService.class);
        AdditionalServiceRepository additionalServiceRepository = mock(AdditionalServiceRepository.class);
        AdvertisementService advertisementServiceMock = mock(AdvertisementService.class);

        AdvertisementOrderService advertisementOrderService = new AdvertisementOrderService(advertisementOrderRepositoryMock,
                emailServiceMock, userInfoServiceMock, advertisementRepositoryMock, advertisementServiceMock, additionalServiceServiceMock, additionalServiceRepository);

        AdvertisementOrder advertisementOrder = new AdvertisementOrder();
        Advertisement ad = new Advertisement();
        Category category = new Category();
        category.setOrderable(true);
        ad.setPrice(20.0);
        ad.setCategory(category);
        ad.setStatus(Status.ACTIVE);
        advertisementOrder.setAdvertisement(ad);

        AdvertisementOrder exceptedOrder = new AdvertisementOrder();
        Advertisement ad1 = new Advertisement();
        ad1.setStatus(Status.SOLD);
        exceptedOrder.setAdvertisement(ad1);

        when(userInfoServiceMock.getCurrentUser()).thenReturn(customerInfoMock);
        when(advertisementServiceMock.findAdvertisementById(anyLong())).thenReturn(ad);
        when(additionalServiceServiceMock.getAdditionalServicesTotalPrice(anySet())).thenReturn((0.0));
        when(advertisementOrderRepositoryMock.save(any())).thenReturn(exceptedOrder);

        //when
        advertisementOrderService.createNewAdvertisementOrder(advertisementDTOMock);

        //then
        verify(emailServiceMock, times(1)).sendEmailAboutPurchase(exceptedOrder, customerInfoMock);
        verify(emailServiceMock, times(1)).sendEmailAboutSell(exceptedOrder, customerInfoMock);
    }
}