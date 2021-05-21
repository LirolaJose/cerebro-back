package com.dataart.cerebro.schedule;

import com.dataart.cerebro.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledEmailTasks {
    private final AdvertisementService advertisementService;

    @Scheduled(cron = "00 31 12 * * ?") // At 12:31:00 AM every day
    public void findExpiringAdvertisements() {
        log.info("Expiring advertisements search launched at {}", LocalDateTime.now());
        advertisementService.findAndNotifyByExpiringInDays(1);
        log.info("Expiring advertisements search is finished");
    }

    @Scheduled(cron = "00 30 12 * * ?") // At 12:30:00 AM every day
    public void findExpiredAdvertisements() {
        log.info("Expired advertisements search launched at {}", LocalDateTime.now());
        advertisementService.findAdvertisementsByExpiredDate();
        log.info("Expired advertisements search is finished");
    }
}






