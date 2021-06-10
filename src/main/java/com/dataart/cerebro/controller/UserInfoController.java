package com.dataart.cerebro.controller;

import com.dataart.cerebro.configuration.model_mapper.UserInfoMapper;
import com.dataart.cerebro.controller.dto.UserInfoDTO;
import com.dataart.cerebro.controller.dto.ValueDTO;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.exception.ValidationException;
import com.dataart.cerebro.service.AdvertisementOrderService;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.UserInfoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Api(tags = "User Info")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;
    private final AdvertisementService advertisementService;
    private final AdvertisementOrderService advertisementOrderService;
    private final UserInfoMapper userInfoMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfoById(@PathVariable Long userId) {
        UserInfoDTO userInfoDTO = userInfoMapper.convertToUserInfoDTO(userInfoService.findUserInfoById(userId));
        return ResponseEntity.ok(userInfoDTO);
    }

    @GetMapping("/")
    public ResponseEntity<?> getCurrentUser() {
        UserInfo userInfo = userInfoService.getCurrentUser();
        return ResponseEntity.ok(new ValueDTO(userInfo == null ? null : userInfoMapper.convertToUserInfoDTO(userInfo)));
    }

    @PostMapping(value = "/{userId}/money/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> increaseMoneyAmount(@PathVariable Long userId, @RequestBody ValueDTO money) {
        double moneyValue = Double.parseDouble((String) money.getValue());
        if (moneyValue <= 0){
            throw new ValidationException("Wrong money format. The number must be positive and minimum 1");
        }
        Double newMoneyAmount = userInfoService.topUpUsersBalance(moneyValue, userId);
        return ResponseEntity.ok(new ValueDTO(newMoneyAmount));
    }
}
