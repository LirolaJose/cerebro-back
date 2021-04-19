package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.service.AdvertisementOrderService;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Api(tags = "User")
public class UserInfoController {
    private final UserInfoService userInfoService;
    private final AdvertisementService advertisementService;
    private final AdvertisementOrderService advertisementOrderService;

    public UserInfoController(UserInfoService userInfoService, AdvertisementService advertisementService, AdvertisementOrderService advertisementOrderService) {
        this.userInfoService = userInfoService;
        this.advertisementService = advertisementService;
        this.advertisementOrderService = advertisementOrderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfoById(@PathVariable Long id) {
        UserInfo userInfo = userInfoService.findUserInfoById(id);

        return userInfo != null
                ? new ResponseEntity<>(userInfo, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/advertisements")
    public ResponseEntity<?> getUsersAdvertisementsByUserId(@PathVariable Long id) {
        List<Advertisement> advertisements = advertisementService.findAdvertisementsByUserInfoId(id);

        return advertisements != null && !advertisements.isEmpty()
                ? new ResponseEntity<>(advertisements, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<?> getUsersOrdersByUserId(@PathVariable Long id) {
        List<AdvertisementOrder> advertisementOrders = advertisementOrderService.findAdvertisementOrdersByUserId(id);

        return advertisementOrders != null && !advertisementOrders.isEmpty()
                ? new ResponseEntity<>(advertisementOrders, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
