package com.dataart.cerebro.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping("/api/login")
    public void authenticate(@ApiParam("User") String email, @ApiParam("Password") String password) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}
