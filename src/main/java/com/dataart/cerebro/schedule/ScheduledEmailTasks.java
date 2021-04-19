package com.dataart.cerebro.schedule;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.repository.AdvertisementRepository;
import com.dataart.cerebro.email.EmailService;
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
    private final EmailService emailService;

    public ScheduledEmailTasks(AdvertisementRepository advertisementRepository, EmailService emailService) {
        this.advertisementRepository = advertisementRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "00 21 17 * * ?") // At 12:10:00 AM every day
    public void findExpiringAdvertisements() {
        log.info("Search for expiring advertisement launched at {}", LocalDateTime.now());
        List<Advertisement> advertisementsList = advertisementRepository.getExpiringAdvertisements();

        Map<String, List<Advertisement>> emailAndAds = advertisementsList.stream()
                .collect(groupingBy(ad -> ad.getOwner().getEmail()));

        if (!emailAndAds.isEmpty()) {
            emailService.sendEmailAboutExpiring(emailAndAds);
        }
        log.info("Search expiring advertisement is finished. Letter was sent to {} addresses", emailAndAds.size());
    }

    @Scheduled(cron = "00 20 17 * * ?") // At 12:05:00 AM every day
    public void findExpiredAdvertisements() {
        log.info("Search for expired advertisement launched at {}", LocalDateTime.now());
        List<com.dataart.cerebro.domain.Advertisement> advertisementsList = advertisementRepository.getExpiredAdvertisements();


        Map<String, List<Advertisement>> emailAndAds = advertisementsList.stream()
                .peek(advertisement -> advertisement.setStatus(Status.CLOSED))
                .peek(advertisementRepository::save)
                .collect(groupingBy(ad -> ad.getOwner().getEmail()));

        if (!emailAndAds.isEmpty()) {
            emailService.sendEmailAboutExpired(emailAndAds);
        }
        log.info("Search expired advertisement is finished. Letter was sent to {} addresses", emailAndAds.size());
    }
}






