package com.dataart.cerebro.controller;

import com.dataart.cerebro.controller.dto.UserInfoDTO;
import com.dataart.cerebro.exception.ValidationException;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.UserInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/registration")
@Api(tags = "Registration")
@Slf4j
public class RegistrationController {
    private final UserInfoService userInfoService;
    private final AdvertisementService advertisementService;

    public RegistrationController(UserInfoService userInfoService, AdvertisementService advertisementService) {
        this.userInfoService = userInfoService;
        this.advertisementService = advertisementService;
    }


    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody @Valid UserInfoDTO userInfoDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrorList = new ArrayList<>(bindingResult.getFieldErrors());
            log.warn("Some parameters are not filled");
            throw new ValidationException(fieldErrorList);
        }
        userInfoService.createNewUserInfo(userInfoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
