package com.dataart.cerebro.service;

import com.dataart.cerebro.repository.AdvertisementOrderRepository;
import com.dataart.cerebro.repository.ServiceRepository;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.email.EmailService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdvertisementOrderServiceTest {

    @Test
    void whenOrderIsAddedThenEmailsAreSend() {
//        //given
//        AdvertisementOrderRepository advertisementOrderRepositoryMock = mock(AdvertisementOrderRepository.class);
//        EmailService emailServiceMock= mock(EmailService.class);
//        ServiceRepository serviceRepositoryMock = mock(ServiceRepository.class);
//        ContactInfo customerInfoMock = mock(ContactInfo.class);
//        AdvertisementOrderService advertisementOrderService = new AdvertisementOrderService(advertisementOrderRepositoryMock, emailServiceMock, serviceRepositoryMock);
//
//        AdvertisementOrder adOrder = new AdvertisementOrder();
//        Advertisement ad = new Advertisement();
//        ad.setPrice(20.0);
//        adOrder.setAdvertisement(ad);
//
//        AdvertisementOrder exceptedOrder = new AdvertisementOrder();
//        Advertisement ad1 = new Advertisement();
////        ad1.setStatus(Status.SOLD);
//        exceptedOrder.setAdvertisement(ad1);
//
//        when(advertisementOrderRepositoryMock.addAdOrder(any(), any(), any(), any())).thenReturn(exceptedOrder);
//
//        //when
//        advertisementOrderService.addAdOrder(adOrder, ad, customerInfoMock);
//
//        //then
//        verify(emailServiceMock, times(1)).sendEmailAboutPurchase(ad, customerInfoMock, exceptedOrder);
//        verify(emailServiceMock, times(1)).sendEmailAboutSell(ad, customerInfoMock, exceptedOrder);
    }
}