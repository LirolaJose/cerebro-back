package com.dataart.cerebro.service;

import com.dataart.cerebro.configuration.security.jwt.JwtProvider;
import com.dataart.cerebro.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserInfoService userInfoService;
    private final JwtProvider jwtProvider;

    public String getTokenAfterAuthentication(String login, String password){
        UserInfo userInfo = userInfoService.findByEmailAndPassword(login, password);
        return jwtProvider.generateToken(userInfo.getEmail());
    }
}
