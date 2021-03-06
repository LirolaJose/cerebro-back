package com.dataart.cerebro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@Slf4j
public class CerebroApplication {

    public static void main(String[] args) {
        SpringApplication.run(CerebroApplication.class, args);
    }
}

