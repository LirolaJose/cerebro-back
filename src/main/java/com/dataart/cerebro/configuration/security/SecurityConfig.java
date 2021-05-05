package com.dataart.cerebro.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();

        http.authorizeRequests()
                    .antMatchers("/api/advertisement/").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/api/image/*").permitAll()
                    .antMatchers("/api/admin/**").hasRole("ADMIN")
//                    .antMatchers("/api/**").permitAll()
                    .antMatchers("/advertisementForm.html").authenticated()
                    .antMatchers("/orderForm.html").authenticated()
                .and().formLogin()
                    .defaultSuccessUrl("/advertisementsList.html")
                .and().logout()
                    .logoutSuccessUrl("/advertisementsList.html")
                .and().httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/v2/api-docs/**");
//        web.ignoring().antMatchers("/swagger.json");
//        web.ignoring().antMatchers("/swagger-ui.html");
//        web.ignoring().antMatchers("/swagger-resources/**");
//        web.ignoring().antMatchers("/webjars/**");
//    }

}

