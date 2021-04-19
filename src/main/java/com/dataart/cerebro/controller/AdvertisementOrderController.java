package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.service.AdvertisementOrderService;
import com.dataart.cerebro.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@Api(tags = "Advertisement Order Controller")
public class AdvertisementOrderController {
    private final AdvertisementOrderService advertisementOrderService;
    private final UserInfoService userInfoService;

    public AdvertisementOrderController(AdvertisementOrderService advertisementOrderService, UserInfoService userInfoService) {
        this.advertisementOrderService = advertisementOrderService;
        this.userInfoService = userInfoService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addOrder(@RequestBody AdvertisementOrder advertisementOrder) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserInfo userInfo = userInfoService.findUserInfoByEmail(authentication.getPrincipal().toString());
        advertisementOrderService.createNewAdvertisementOrder(advertisementOrder);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
