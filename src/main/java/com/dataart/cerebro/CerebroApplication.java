package com.dataart.cerebro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
@Slf4j
public class CerebroApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CerebroApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Application is run");
    }
}
