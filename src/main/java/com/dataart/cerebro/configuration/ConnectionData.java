package com.dataart.cerebro.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

// FIXME: 5/5/2021 remove??
@Configuration
public class ConnectionData {
    @Value("${spring.datasource.url}")
    public String URL;
    @Value("${spring.datasource.username}")
    public String USER;
    @Value("${spring.datasource.password}")
    public String PASSWORD;

}
