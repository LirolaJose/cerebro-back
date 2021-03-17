package com.dataart.cerebro.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ConnectionData {
    @Value("${spring.datasource.url}")
    public String URL;
    @Value("${spring.datasource.username}")
    public String USER;
    @Value("${spring.datasource.password}")
    public String PASSWORD;

}
