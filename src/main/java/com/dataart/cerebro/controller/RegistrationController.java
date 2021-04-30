package com.dataart.cerebro.controller;

import com.dataart.cerebro.controller.dto.UserInfoDTO;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@Api(tags = "Registration")
public class RegistrationController {
    private final UserInfoService userInfoService;
    private final AdvertisementService advertisementService;

    public RegistrationController(UserInfoService userInfoService, AdvertisementService advertisementService) {
        this.userInfoService = userInfoService;
        this.advertisementService = advertisementService;
    }


    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody UserInfoDTO userInfoDTO) {
        userInfoService.createNewUserInfo(userInfoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
