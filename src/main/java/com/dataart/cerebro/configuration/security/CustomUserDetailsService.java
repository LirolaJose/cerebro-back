package com.dataart.cerebro.configuration.security;

import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.service.UserInfoService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserInfoService userInfoService;

    public CustomUserDetailsService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoService.findUserInfoByEmail(email);
        return CustomUserDetails.fromUserInfoToCustomUserDetails(userInfo);
    }
}