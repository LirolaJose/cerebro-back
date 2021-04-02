package com.dataart.cerebro.schedule;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.StatusDTO;
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
    private final AdvertisementDAO advertisementDAO;
    private final EmailService emailService;

    public ScheduledEmailTasks(AdvertisementDAO advertisementDAO, EmailService emailService) {
        this.advertisementDAO = advertisementDAO;
        this.emailService = emailService;
    }

    @Scheduled(cron = "00 10 12 * * ?") // At 12:10:00 am every day
    public void findExpiringAdvertisements() {
        log.info("Search for expiring advertisement launched at {}", LocalDateTime.now());
        List<AdvertisementDTO> advertisementsList = advertisementDAO.getExpiringAdvertisements();

        Map<String, List<AdvertisementDTO>> emailAndAds = advertisementsList.stream()
                .collect(groupingBy(ad -> ad.getOwner().getEmail()));

        if (!emailAndAds.isEmpty()) {
            emailService.sendEmailAboutExpiring(emailAndAds);
        }
        log.info("Search expiring ads is finished. Letter was sent to {} addresses", emailAndAds.size());
    }

    @Scheduled(cron = "00 12 12 * * ?") // At 12:12:00 am every day
//    @Scheduled (fixedRate = 10000)
    public void findExpiredAdvertisements() {
        log.info("Search for expired advertisement launched at {}", LocalDateTime.now());
        List<AdvertisementDTO> advertisementsList = advertisementDAO.getExpiredAdvertisements();
        int statusId = StatusDTO.CLOSED.getId();

        Map<String, List<AdvertisementDTO>> emailAndAds = advertisementsList.stream()
                .peek(ad -> advertisementDAO.updateAdvertisementStatus(statusId, ad))
                .collect(groupingBy(ad -> ad.getOwner().getEmail()));

        if (!emailAndAds.isEmpty()) {
            emailService.sendEmailAboutExpired(emailAndAds);
        }
        log.info("Search expired ads is finished. Letter was sent to {} addresses", emailAndAds.size());
    }
}






