package com.dataart.cerebro.controller;

import com.dataart.cerebro.controller.dto.ValueDTO;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.service.AdvertisementOrderService;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserInfoController {
    private final UserInfoService userInfoService;
    private final AdvertisementService advertisementService;
    private final AdvertisementOrderService advertisementOrderService;

    public UserInfoController(UserInfoService userInfoService, AdvertisementService advertisementService, AdvertisementOrderService advertisementOrderService) {
        this.userInfoService = userInfoService;
        this.advertisementService = advertisementService;
        this.advertisementOrderService = advertisementOrderService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfoById(@PathVariable Long userId) {
        UserInfo userInfo = userInfoService.findUserInfoById(userId);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @GetMapping("/{userId}/advertisements")
    public ResponseEntity<?> getUsersAdvertisementsByUserId(@PathVariable Long userId) {
        List<Advertisement> advertisements = advertisementService.findAdvertisementsByUserInfoId(userId);
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<?> getUsersOrdersByUserId(@PathVariable Long userId) {
        List<AdvertisementOrder> advertisementOrders = advertisementOrderService.findAdvertisementOrdersByUserId(userId);
        return new ResponseEntity<>(advertisementOrders, HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public ResponseEntity<?> getCurrentUser() {
        UserInfo userInfo = userInfoService.getCurrentUser();
        return new ResponseEntity<>(new ValueDTO(userInfo), HttpStatus.OK);
    }
}
