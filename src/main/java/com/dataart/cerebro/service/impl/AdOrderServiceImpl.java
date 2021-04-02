package com.dataart.cerebro.service.impl;

import com.dataart.cerebro.dao.AdOrderDAO;
import com.dataart.cerebro.dao.ServiceDAO;
import com.dataart.cerebro.dto.AdOrderDTO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.service.AdOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AdOrderServiceImpl implements AdOrderService {
    private final AdOrderDAO adOrderDAO;
    private final EmailService emailService;
    private final ServiceDAO serviceDAO;

    public AdOrderServiceImpl(AdOrderDAO adOrderDAO, EmailService emailService, ServiceDAO serviceDAO) {
        this.adOrderDAO = adOrderDAO;
        this.emailService = emailService;
        this.serviceDAO = serviceDAO;
    }

    @Override
    public void adAdOrder(AdOrderDTO adOrder, AdvertisementDTO advertisement, ContactInfoDTO customerInfo) {
        LocalDateTime orderTime = LocalDateTime.now();
        Double totalPrice = advertisement.getPrice() + serviceDAO.getTotalPriceServices(adOrder.getServicesSet());

        adOrder.setTotalPrice(totalPrice);
        AdOrderDTO order = adOrderDAO.addAdOrder(adOrder, orderTime, advertisement, customerInfo);

        emailService.sendEmailAboutPurchase(advertisement, customerInfo, order);
        emailService.sendEmailAboutSell(advertisement, customerInfo, order);
    }
}
