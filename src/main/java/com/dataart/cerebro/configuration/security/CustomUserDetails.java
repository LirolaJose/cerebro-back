package com.dataart.cerebro.configuration.security;

import com.dataart.cerebro.domain.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class CustomUserDetails implements UserDetails {

    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetails fromUserInfoToCustomUserDetails(UserInfo userInfo) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.login = userInfo.getEmail();
        customUserDetails.password = userInfo.getPassword();
        customUserDetails.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userInfo.getRole().getName()));
        return customUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
