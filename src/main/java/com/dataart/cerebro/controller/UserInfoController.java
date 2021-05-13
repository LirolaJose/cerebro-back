package com.dataart.cerebro.controller;

import com.dataart.cerebro.controller.dto.UserInfoDTO;
import com.dataart.cerebro.controller.dto.ValueDTO;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.exception.ValidationException;
import com.dataart.cerebro.service.AdvertisementOrderService;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Api(tags = "User Info")
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
        UserInfoDTO userInfoDTO = new UserInfoDTO(userInfoService.findUserInfoById(userId));
        return ResponseEntity.ok(new ValueDTO(userInfoDTO));
    }

    // TODO: 11.05.2021 change Advertisement to AdvertisementDTO
    @GetMapping("/{userId}/advertisements")
    public ResponseEntity<?> getUsersAdvertisementsByUserId(@PathVariable Long userId) {
        List<Advertisement> advertisements = advertisementService.findAdvertisementsByUserInfoId(userId);
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    // TODO: 11.05.2021 change AdvertisementOrder to AdvertisementOrderDTO
    @GetMapping("/{userId}/orders")
    public ResponseEntity<?> getUsersOrdersByUserId(@PathVariable Long userId) {
        List<AdvertisementOrder> advertisementOrders = advertisementOrderService.findAdvertisementOrdersByUserId(userId);
        return new ResponseEntity<>(advertisementOrders, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<?> getCurrentUser() {
        UserInfo userInfo = userInfoService.getCurrentUser();
        return ResponseEntity.ok(new ValueDTO(userInfo == null ? null : new UserInfoDTO(userInfo)));
    }

    @PostMapping(value = "/{userId}/money/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> increaseMoneyAmount(@PathVariable Long userId, @RequestBody ValueDTO money) {
        double moneyValue = Double.parseDouble((String) money.getValue());
        if (moneyValue <= 0){
            throw new ValidationException("Wrong money format. The number must be positive and minimum 1");
        }
        userInfoService.increaseCurrentUserMoneyAmount(moneyValue, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
