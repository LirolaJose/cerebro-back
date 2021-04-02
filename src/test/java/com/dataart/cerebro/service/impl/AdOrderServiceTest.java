package com.dataart.cerebro.service.impl;

import com.dataart.cerebro.dao.AdOrderDAO;
import com.dataart.cerebro.dao.ServiceDAO;
import com.dataart.cerebro.domain.AdOrderDTO;
import com.dataart.cerebro.domain.AdvertisementDTO;
import com.dataart.cerebro.domain.ContactInfoDTO;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.service.AdOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdOrderServiceTest {

    @Test
    void whenOrderIsAddedThenEmailsAreSend() {
        //given
        AdOrderDAO adOrderDAOMock = mock(AdOrderDAO.class);
        EmailService emailServiceMock= mock(EmailService.class);
        ServiceDAO serviceDAOMock = mock(ServiceDAO.class);
        ContactInfoDTO customerInfoMock = mock(ContactInfoDTO.class);
        AdOrderService adOrderService = new AdOrderServiceImpl(adOrderDAOMock, emailServiceMock, serviceDAOMock);

        AdvertisementDTO ad = new AdvertisementDTO();
        ad.setPrice(20.0);
        AdOrderDTO adOrderDTO = new AdOrderDTO();
        adOrderDTO.setAdvertisement(ad);

        AdOrderDTO exceptedOrder = new AdOrderDTO();
        AdvertisementDTO ad1 = new AdvertisementDTO();
        ad1.setStatus(Status.SOLD);
        exceptedOrder.setAdvertisement(ad1);

        when(adOrderDAOMock.addAdOrder(any(), any(), any(), any())).thenReturn(exceptedOrder);

        //when
        adOrderService.addAdOrder(adOrderDTO, ad, customerInfoMock);

        //then
        verify(emailServiceMock, times(1)).sendEmailAboutPurchase(ad, customerInfoMock, exceptedOrder);
        verify(emailServiceMock, times(1)).sendEmailAboutSell(ad, customerInfoMock, exceptedOrder);
    }
}