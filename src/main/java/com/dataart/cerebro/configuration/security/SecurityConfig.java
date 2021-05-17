package com.dataart.cerebro.configuration.security;

import com.dataart.cerebro.configuration.security.jwt.JwtFilter;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                    .authorizeRequests()
                    .antMatchers("/api/registration").permitAll()
                    .antMatchers("/registrationForm.html").permitAll()
                    .antMatchers("/api/auth", "/api/logout").permitAll()
                    .antMatchers("/loginForm.html").permitAll()
                    .antMatchers("/api/advertisement/**").permitAll()
                    .antMatchers("/api/user/").permitAll()
                    .antMatchers("/advertisementsList.html").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/api/image/**").permitAll()
                    .antMatchers("/api/additionalServices/advertisement/**").permitAll()
//                    .antMatchers("/advertisementForm.html").hasRole("USER")
                    .antMatchers("/api/order/").authenticated()
//                    .antMatchers("/orderForm.html").authenticated()
                    .antMatchers("/api/**").authenticated()

                .and()
                    .formLogin()
                    .loginPage("/loginForm.html")
                    .failureUrl("/loginForm.html?error=true")
                .and()
                    .logout()
                    .logoutSuccessUrl("/advertisementsList.html")
                .and()
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }
}

