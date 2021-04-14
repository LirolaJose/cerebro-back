package com.dataart.cerebro.service.impl;

import com.dataart.cerebro.dao.AdvertisementOrderRepository;
import com.dataart.cerebro.dao.ServiceRepository;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.ContactInfo;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.service.AdvertisementOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AdvertisementOrderServiceImpl implements AdvertisementOrderService {
    private final AdvertisementOrderRepository advertisementOrderRepository;
    private final EmailService emailService;
    private final ServiceRepository serviceRepository;

    public AdvertisementOrderServiceImpl(AdvertisementOrderRepository advertisementOrderRepository, EmailService emailService, ServiceRepository serviceRepository) {
        this.advertisementOrderRepository = advertisementOrderRepository;
        this.emailService = emailService;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void addAdOrder(AdvertisementOrder adOrder, Advertisement advertisement, ContactInfo customerInfo) {
        Double totalPrice = advertisement.getPrice() + serviceRepository.getTotalPriceServices(adOrder.getServices());

        adOrder.setTotalPrice(totalPrice);
        AdvertisementOrder order = advertisementOrderRepository.addAdOrder(adOrder, LocalDateTime.now(), advertisement, customerInfo);

        emailService.sendEmailAboutPurchase(advertisement, customerInfo, order);
        emailService.sendEmailAboutSell(advertisement, customerInfo, order);
    }
}
