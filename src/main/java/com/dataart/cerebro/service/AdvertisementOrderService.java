package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.repository.AdvertisementOrderRepository;
import com.dataart.cerebro.repository.ServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AdvertisementOrderService {
    private final AdvertisementOrderRepository advertisementOrderRepository;
    private final EmailService emailService;
    private final ServiceRepository serviceRepository;
    private final UserInfoService userInfoService;

    public AdvertisementOrderService(AdvertisementOrderRepository advertisementOrderRepository, EmailService emailService, ServiceRepository serviceRepository, UserInfoService userInfoService) {
        this.advertisementOrderRepository = advertisementOrderRepository;
        this.emailService = emailService;
        this.serviceRepository = serviceRepository;
        this.userInfoService = userInfoService;
    }

    public List<AdvertisementOrder> findAdvertisementOrdersByUserId(Long id) {
        return advertisementOrderRepository.findAdvertisementOrdersByAdvertisementOwnerId(id);
    }

    public void createNewAdvertisementOrder(AdvertisementOrder advertisementOrder) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo customer = userInfoService.findUserInfoByEmail(authentication.getName());
        try {
            advertisementOrder.setOrderTime(LocalDateTime.now());
//            advertisementOrder.setTotalPrice();
//            advertisementOrder.setAdvertisement();
            advertisementOrder.setCustomer(customer);
            advertisementOrderRepository.save(advertisementOrder);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new NotFoundException("Incorrect data");
        }
        emailService.sendEmailAboutPurchase(advertisementOrder, customer);
        emailService.sendEmailAboutSell(advertisementOrder, customer);
    }
}
