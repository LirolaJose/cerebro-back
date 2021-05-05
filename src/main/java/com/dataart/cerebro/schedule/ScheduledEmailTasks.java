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
    private final AdvertisementService advertisementService;

    public ScheduledEmailTasks(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @Scheduled(cron = "00 31 12 * * ?") // At 12:31:00 AM every day
//    @Scheduled(fixedRate = 500000)
    public void findExpiringAdvertisements() {
        log.info("Expiring advertisements search launched at {}", LocalDateTime.now());
        advertisementService.findAdvertisementsByExpiringDate(Status.ACTIVE.getId(), 1);
        log.info("Expiring advertisements search is finished");
    }

    @Scheduled(cron = "00 30 12 * * ?") // At 12:30:00 AM every day
//        @Scheduled(fixedRate = 500000)
    public void findExpiredAdvertisements() {
        log.info("Expired advertisements search launched at {}", LocalDateTime.now());
        advertisementService.findAdvertisementsByExpiredDate(Status.ACTIVE.getId(), 0);
        log.info("Expired advertisements search is finished");
    }
}






