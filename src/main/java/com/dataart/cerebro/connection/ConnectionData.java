package com.dataart.cerebro.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
public class ConnectionData {
//    @Value("${spring.datasource.url}")
    public String URL = "jdbc:postgresql://localhost:5432/board";
//    @Value("${spring.datasource.username}")
    public String USER = "postgres";
//    @Value("${spring.datasource.password}")
    public String PASSWORD = "12345";
}
