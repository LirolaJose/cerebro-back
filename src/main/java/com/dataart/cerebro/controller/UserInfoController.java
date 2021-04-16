package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.service.AdvertisementOrderService;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cerebro/api")
@Api(tags = "UserInfo")
public class UserInfoController {
    private final UserInfoService userInfoService;
    private final AdvertisementService advertisementService;
    private final AdvertisementOrderService advertisementOrderService;

    public UserInfoController(UserInfoService userInfoService, AdvertisementService advertisementService, AdvertisementOrderService advertisementOrderService) {
        this.userInfoService = userInfoService;
        this.advertisementService = advertisementService;
        this.advertisementOrderService = advertisementOrderService;
    }

    @GetMapping("/GET/user/{id}")
    public UserInfo getUserInfoById (@PathVariable Long id){
        return userInfoService.findUserInfoById(id);
    }

    @GetMapping("/GET/user/{id}/advertisements")
    public List<Advertisement> getUsersAdvertisementsByUserId (@PathVariable Long id){
        return advertisementService.findAdvertisementsByUserInfoId(id);
    }
    @GetMapping("/GET/user/{id}/orders")
    public List<AdvertisementOrder> getUsersOrdersByUserId(@PathVariable Long id){
        return advertisementOrderService.findAdvertisementOrdersByUserId(id);
    }

    @GetMapping("/GET/user/{userId}/order/{orderId}")
    public AdvertisementOrder getUsersOrderByOrderId(@PathVariable Long userId, @PathVariable Long orderId){
        return advertisementOrderService.findAdvertisementOrderByOrderId(orderId);
    }

}
