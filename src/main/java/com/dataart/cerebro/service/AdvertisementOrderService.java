package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.repository.AdvertisementOrderRepository;
import com.dataart.cerebro.repository.ServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdvertisementOrderService {
    private final AdvertisementOrderRepository advertisementOrderRepository;
    private final EmailService emailService;
    private final ServiceRepository serviceRepository;

    public AdvertisementOrderService(AdvertisementOrderRepository advertisementOrderRepository, EmailService emailService, ServiceRepository serviceRepository) {
        this.advertisementOrderRepository = advertisementOrderRepository;
        this.emailService = emailService;
        this.serviceRepository = serviceRepository;
    }

    public List<AdvertisementOrder> findAdvertisementOrdersByUserId(Long id) {
        return advertisementOrderRepository.findAdvertisementOrdersByAdvertisementOwnerId(id);
    }

    public void createNewAdvertisementOrder(AdvertisementOrder advertisementOrder, UserInfo customer) {
        advertisementOrderRepository.save(advertisementOrder);
        emailService.sendEmailAboutPurchase(advertisementOrder, customer);
        emailService.sendEmailAboutSell(advertisementOrder, customer);
    }


    public void addAdOrder(AdvertisementOrder adOrder, Advertisement advertisement) {
//        Double totalPrice = advertisement.getPrice() + serviceRepository.getTotalPriceServices(adOrder.getServices());
//
//        adOrder.setTotalPrice(totalPrice);
//        AdvertisementOrder order = advertisementOrderRepository.addAdOrder(adOrder, LocalDateTime.now(), advertisement, customerInfo);
//
//        emailService.sendEmailAboutPurchase(advertisement, customerInfo, order);
//        emailService.sendEmailAboutSell(advertisement, customerInfo, order);
    }
}
