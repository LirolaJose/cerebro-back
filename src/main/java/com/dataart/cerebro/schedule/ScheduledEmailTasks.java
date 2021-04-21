package com.dataart.cerebro.schedule;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.repository.AdvertisementRepository;
import com.dataart.cerebro.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Component
public class ScheduledEmailTasks {
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementService advertisementService;
    private final EmailService emailService;

    public ScheduledEmailTasks(AdvertisementRepository advertisementRepository, AdvertisementService advertisementService, EmailService emailService) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementService = advertisementService;
        this.emailService = emailService;
    }

    @Scheduled(cron = "00 36 12 * * ?") // At 12:31:00 AM every day
//    @Scheduled(fixedRate = 500000)
    public void findExpiringAdvertisements() {
        log.info("Search for expiring advertisement launched at {}", LocalDateTime.now());
        advertisementService.findAdvertisementsByExpiringDate(Status.ACTIVE.getId(), 1);
        log.info("Search expiring advertisement is finished");
    }

    @Scheduled(cron = "00 35 12 * * ?") // At 12:30:00 AM every day
//        @Scheduled(fixedRate = 500000)
    public void findExpiredAdvertisements() {
        log.info("Search for expired advertisement launched at {}", LocalDateTime.now());
        advertisementService.findAdvertisementsByExpiredDate(Status.ACTIVE.getId(), 0);
        log.info("Search expired advertisement is finished");
    }
}






