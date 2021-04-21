package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@Api(tags = "Registration")
public class RegistrationController {
    private final UserInfoService userInfoService;

    public RegistrationController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @CrossOrigin
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody UserInfo userInfo){
        userInfoService.createNewUserInfo(userInfo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
