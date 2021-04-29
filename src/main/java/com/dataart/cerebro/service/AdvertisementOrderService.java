package com.dataart.cerebro.service;

import com.dataart.cerebro.controller.dto.AdvertisementOrderDTO;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.repository.AdditionalServiceRepository;
import com.dataart.cerebro.repository.AdvertisementOrderRepository;
import com.dataart.cerebro.repository.AdvertisementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AdvertisementOrderService {
    private final AdvertisementOrderRepository advertisementOrderRepository;
    private final EmailService emailService;
    private final AdditionalServiceRepository additionalServiceRepository;
    private final UserInfoService userInfoService;
    private final AdvertisementRepository advertisementRepository;
    private final AdditionalServiceService additionalServiceService;

    public AdvertisementOrderService(AdvertisementOrderRepository advertisementOrderRepository, EmailService emailService, AdditionalServiceRepository additionalServiceRepository, UserInfoService userInfoService, AdvertisementRepository advertisementRepository, AdditionalServiceService additionalServiceService) {
        this.advertisementOrderRepository = advertisementOrderRepository;
        this.emailService = emailService;
        this.additionalServiceRepository = additionalServiceRepository;
        this.userInfoService = userInfoService;
        this.advertisementRepository = advertisementRepository;
        this.additionalServiceService = additionalServiceService;
    }

    public List<AdvertisementOrder> findAdvertisementOrdersByUserId(Long id) {
        return advertisementOrderRepository.findAdvertisementOrdersByAdvertisementOwnerId(id);
    }

    @Transactional
    public void createNewAdvertisementOrder(AdvertisementOrderDTO advertisementOrderDTO) {
        UserInfo customer = userInfoService.getCurrentUser();
        log.info("User with email {} creates new order", customer.getEmail());
        AdvertisementOrder advertisementOrder = new AdvertisementOrder();
        Advertisement advertisement = advertisementRepository.findAdvertisementById(advertisementOrderDTO.getAdvertisementId());
        advertisementOrder.setOrderTime(LocalDateTime.now());
        Double totalPrice = advertisement.getPrice() +
                additionalServiceService.getAdditionalServicesTotalPrice(advertisementOrderDTO.getAdditionalServicesId());

        userInfoService.changeMoneyAmount(customer, advertisement.getOwner(), totalPrice);
        advertisementOrder.setTotalPrice(totalPrice);
        advertisementOrder.setAdvertisement(advertisement);
        advertisementOrder.setCustomer(customer);
        AdvertisementOrder newOrder = advertisementOrderRepository.save(advertisementOrder);

        advertisement.setStatus(Status.SOLD);
        advertisementRepository.save(advertisement);

        log.info("New order added (id = {})", newOrder.getId());
        emailService.sendEmailAboutPurchase(newOrder, customer);
        emailService.sendEmailAboutSell(newOrder, customer);
    }
}
