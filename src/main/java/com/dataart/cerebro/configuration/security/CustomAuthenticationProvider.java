package com.dataart.cerebro.configuration.security;

import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.repository.UserInfoRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserInfoRepository userInfoRepository;

    public CustomAuthenticationProvider(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = DigestUtils.md5Hex(authentication.getCredentials().toString());

        UserInfo userInfo = userInfoRepository.findUserInfoByEmail(username);
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
