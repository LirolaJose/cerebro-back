package com.dataart.cerebro.controller;

import com.dataart.cerebro.controller.dto.UserInfoDTO;
import com.dataart.cerebro.exception.ValidationException;
import com.dataart.cerebro.service.UserInfoService;
import io.swagger.annotations.Api;
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
import java.util.List;

@RestController
@RequestMapping("/registration")
@Api(tags = "Registration")
public class RegistrationController {
    private final UserInfoService userInfoService;

    public RegistrationController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }


    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody @Valid UserInfoDTO userInfoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            throw new ValidationException(fieldErrorList);
        }
        userInfoService.createNewUserInfo(userInfoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
