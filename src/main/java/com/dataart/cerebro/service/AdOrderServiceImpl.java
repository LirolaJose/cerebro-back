package com.dataart.cerebro.service;

import com.dataart.cerebro.dao.AdOrderDAO;
import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdOrderDTO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AdOrderServiceImpl implements AdOrderService {
    final AdOrderDAO adOrderDAO;
    final AdvertisementDAO advertisementDAO;
    final EmailService emailService;

    public AdOrderServiceImpl(AdOrderDAO adOrderDAO, AdvertisementDAO advertisementDAO, EmailService emailService) {
        this.adOrderDAO = adOrderDAO;
        this.advertisementDAO = advertisementDAO;
        this.emailService = emailService;
    }

    @Override
    public void adAdOrder(AdOrderDTO order, AdvertisementDTO advertisementDTO, ContactInfoDTO customerInfo) {
        LocalDateTime orderTime = LocalDateTime.now();
        Double totalPrice = advertisementDTO.getPrice();
        order.setTotalPrice(totalPrice);
        adOrderDAO.addAdOrder(order, orderTime, advertisementDTO, customerInfo);

        emailService.sendEmailAboutPurchase(advertisementDTO, customerInfo);
        emailService.sendEmailAboutSell(advertisementDTO, customerInfo);
    }
}
