package com.dataart.cerebro.service;

import com.dataart.cerebro.repository.AdvertisementOrderRepository;
import com.dataart.cerebro.repository.ServiceRepository;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.ContactInfo;
import com.dataart.cerebro.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<AdvertisementOrder> findAdvertisementOrdersByUserId(Long id){
        return advertisementOrderRepository.findAdvertisementOrdersByAdvertisement_Owner_Id(id);
    }

    public AdvertisementOrder findAdvertisementOrderByOrderId(Long id){
        return advertisementOrderRepository.findAdvertisementOrdersById(id);
    }








    public void addAdOrder(AdvertisementOrder adOrder, Advertisement advertisement, ContactInfo customerInfo) {
        Double totalPrice = advertisement.getPrice() + serviceRepository.getTotalPriceServices(adOrder.getServices());

        adOrder.setTotalPrice(totalPrice);
        AdvertisementOrder order = advertisementOrderRepository.addAdOrder(adOrder, LocalDateTime.now(), advertisement, customerInfo);

        emailService.sendEmailAboutPurchase(advertisement, customerInfo, order);
        emailService.sendEmailAboutSell(advertisement, customerInfo, order);
    }
}
