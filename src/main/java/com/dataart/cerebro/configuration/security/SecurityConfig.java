package com.dataart.cerebro.configuration.security;

import com.dataart.cerebro.configuration.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtFilter jwtFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()

                .and()
                    .authorizeRequests()
                    .antMatchers("/api/registration").permitAll()
                    .antMatchers("/registrationForm.html").permitAll()
                    .antMatchers("/api/auth", "/api/logout").permitAll()
                    .antMatchers("/loginForm.html").permitAll()
                    .antMatchers("/api/advertisement/**").permitAll()
                    .antMatchers("/api/user/").permitAll()
                    .antMatchers("/advertisementsList.html").permitAll()
                    .antMatchers("/api/image/**").permitAll()
                    .antMatchers("/api/additionalServices/advertisement/**").permitAll()
                    .antMatchers("/api/order/").authenticated()
                    .antMatchers("/api/**").authenticated()

                .and()
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }
}

