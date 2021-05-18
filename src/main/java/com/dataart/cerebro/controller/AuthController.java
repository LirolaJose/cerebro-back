package com.dataart.cerebro.controller;

import com.dataart.cerebro.configuration.security.jwt.JwtProvider;
import com.dataart.cerebro.controller.dto.UserInfoDTO;
import com.dataart.cerebro.controller.dto.ValueDTO;
import com.dataart.cerebro.exception.ValidationException;
import com.dataart.cerebro.service.AuthService;
import com.dataart.cerebro.service.UserInfoService;
import com.dataart.cerebro.util.SecurityUtils;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = "Auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserInfoService userInfoService;
    private final JwtProvider jwtProvider;
    private final AuthService authService;


    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody @Valid UserInfoDTO userInfoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            throw new ValidationException(fieldErrorList);
        }
        userInfoService.createNewUserInfo(userInfoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> auth(@RequestBody @Valid AuthRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            throw new ValidationException(fieldErrorList);
        }
        String token = authService.getTokenAfterAuthentication(request.getLogin(), request.getPassword());
        return ResponseEntity.ok(new ValueDTO(token));
    }

    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(){
        jwtProvider.logout(SecurityUtils.getCurrentUserEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Data
    public static class AuthRequest {
        @NotEmpty
        private String login;
        @NotEmpty
        private String password;
    }

}

