package com.dataart.cerebro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })
@Slf4j
public class CerebroApplication {

	public static void main(String[] args) {
		SpringApplication.run(CerebroApplication.class, args);
		log.info("Application is run");
	}

}
