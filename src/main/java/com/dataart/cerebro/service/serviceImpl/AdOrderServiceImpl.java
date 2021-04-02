package com.dataart.cerebro.service.serviceImpl;

import com.dataart.cerebro.dao.AdOrderDAO;
import com.dataart.cerebro.dao.AdvertisementDAO;
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
    public void adAdOrder(AdOrderDTO adOrderDTO, AdvertisementDTO advertisementDTO, ContactInfoDTO customerInfo) {
        LocalDateTime orderTime = LocalDateTime.now();
        Double totalPrice = advertisementDTO.getPrice() + serviceDAO.getTotalPriceServices(adOrderDTO.getServicesSet());

        adOrderDTO.setTotalPrice(totalPrice);
        AdOrderDTO order = adOrderDAO.addAdOrder(adOrderDTO, orderTime, advertisementDTO, customerInfo);

        emailService.sendEmailAboutPurchase(advertisementDTO, customerInfo, order);
        emailService.sendEmailAboutSell(advertisementDTO, customerInfo, order);
    }
}
