package com.dataart.cerebro;

import com.dataart.cerebro.connection.ConnectionData;
import com.dataart.cerebro.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })
@Slf4j
public class CerebroApplication implements CommandLineRunner {
	final ConnectionData connectionData;
	final AdvertisementService advertisementService;

	public CerebroApplication(ConnectionData connectionData, AdvertisementService advertisementService) {
		this.connectionData = connectionData;
		this.advertisementService = advertisementService;
	}

	public static void main(String[] args) {
		SpringApplication.run(CerebroApplication.class, args);}

	@Override
	public void run(String... args){
		log.info("Application is run");
		System.out.println(advertisementService.getAllAdvertisement());
	}
}
