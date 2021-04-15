package com.dataart.cerebro.schedule;

import com.dataart.cerebro.repository.AdvertisementRepository;
import com.dataart.cerebro.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(cron = "00 10 12 * * ?") // At 12:10:00 AM every day
    public void findExpiringAdvertisements() {
//        log.info("Search for expiring advertisement launched at {}", LocalDateTime.now());
//        List<com.dataart.cerebro.domain.Advertisement> advertisementsList = advertisementRepository.getExpiringAdvertisements();
//
//        Map<String, List<com.dataart.cerebro.domain.Advertisement>> emailAndAds = advertisementsList.stream()
//                .collect(groupingBy(ad -> ad.getOwner().getEmail()));
//
//        if (!emailAndAds.isEmpty()) {
//            emailService.sendEmailAboutExpiring(emailAndAds);
//        }
//        log.info("Search expiring advertisement is finished. Letter was sent to {} addresses", emailAndAds.size());
    }

    @Scheduled(cron = "00 05 12 * * ?") // At 12:05:00 AM every day
    public void findExpiredAdvertisements() {
//        log.info("Search for expired advertisement launched at {}", LocalDateTime.now());
//        List<com.dataart.cerebro.domain.Advertisement> advertisementsList = advertisementRepository.getExpiredAdvertisements();
//
//
//        Map<String, List<com.dataart.cerebro.domain.Advertisement>> emailAndAds = advertisementsList.stream()
//                .peek(ad -> advertisementRepository.updAdvertisementStatus(ad, Status.CLOSED))
//                .collect(groupingBy(ad -> ad.getOwner().getEmail()));
//
//        if (!emailAndAds.isEmpty()) {
//            emailService.sendEmailAboutExpired(emailAndAds);
//        }
//        log.info("Search expired advertisement is finished. Letter was sent to {} addresses", emailAndAds.size());
    }
}






