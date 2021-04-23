package com.dataart.cerebro.configuration.security;

import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.service.UserInfoService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserInfoService userInfoService;

    public CustomAuthenticationProvider(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = userInfoService.encryptPassword(authentication.getCredentials().toString());

        UserInfo userInfo = userInfoService.findUserInfoByEmail(username);
        if (userInfo == null || !password.equals(userInfo.getPassword())) {
            throw new CredentialsExpiredException("Invalid credentials");
        }
        UserDetails principal = User.builder()
                .username(userInfo.getEmail())
                .password(userInfo.getPassword())
                .roles(userInfo.getRole().getName())
                .build();
        return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
