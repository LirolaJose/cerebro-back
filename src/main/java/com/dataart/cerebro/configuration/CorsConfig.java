package com.dataart.cerebro.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .maxAge(3600);
//                .exposedHeaders("Authorization", "Content-Disposition", "X-Total-Count")
//                .allowCredentials(true);
    }
}
