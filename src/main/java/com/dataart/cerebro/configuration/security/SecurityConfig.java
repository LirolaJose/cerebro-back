package com.dataart.cerebro.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/advertisement/**").permitAll()
                .antMatchers("/api/user/").permitAll()
                .antMatchers("/advertisementsList.html").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/image/**").permitAll()
                .antMatchers("/api/additionalServices/advertisement/**").permitAll()
                .antMatchers("/advertisementForm.html").authenticated()
                .antMatchers("/api/order/").authenticated()
                .antMatchers("/orderForm.html").authenticated()
                .antMatchers("/api/**").authenticated()
                    .and().formLogin()
                .defaultSuccessUrl("/advertisementsList.html")
                    .and().logout()
                .logoutSuccessUrl("/advertisementsList.html")
                    .and().httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider);
    }
}

