package com.dataart.cerebro.schedule;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.StatusDTO;
import com.dataart.cerebro.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Component
public class ScheduledEmailTasks {
    final AdvertisementDAO advertisementDAO;
    final EmailService emailService;
    List<AdvertisementDTO> sentLettersExpiring = new ArrayList<>();
    List<AdvertisementDTO> sentLettersExpired = new ArrayList<>();

    public ScheduledEmailTasks(AdvertisementDAO advertisementDAO, EmailService emailService) {
        this.advertisementDAO = advertisementDAO;
        this.emailService = emailService;
    }

    @Scheduled(cron = "00 00 11 * * ?") // At 11:00:00 am every day
    public void findExpiringAdvertisements() {
        log.info("Search for expiring advertisement launched at {}", LocalDateTime.now());
        List<AdvertisementDTO> advertisementsList = advertisementDAO.getExpiringAdvertisements();

        Map<String, List<AdvertisementDTO>> emailAndAds = advertisementsList.stream()
                .filter(ad -> !sentLettersExpiring.contains(ad))
                .peek(ad -> sentLettersExpiring.add(ad))
                .collect(groupingBy(ad -> ad.getOwner().getEmail()));

        if (!emailAndAds.isEmpty()) {
            emailService.sendEmailAboutExpiring(emailAndAds);
        }
        log.info("Search expiring ads is finished. Letter was sent to {} addresses", emailAndAds.size());
    }

    @Scheduled(cron = "00 05 11 * * ?") // At 11:05:00 am every day
//    @Scheduled (fixedRate = 10000)
    public void findExpiredAdvertisements() {
        log.info("Search for expired advertisement launched at {}", LocalDateTime.now());
        List<AdvertisementDTO> advertisementsList = advertisementDAO.getExpiredAdvertisements();
        int statusId = StatusDTO.CLOSED.getId();

        Map<String, List<AdvertisementDTO>> emailAndAds = advertisementsList.stream()
                .filter(ad -> !sentLettersExpired.contains(ad))
                .peek(ad -> sentLettersExpired.add(ad))
                .peek(ad -> advertisementDAO.updateAdvertisementStatus(statusId, ad))
                .collect(groupingBy(ad -> ad.getOwner().getEmail()));

        if (!emailAndAds.isEmpty()) {
            emailService.sendEmailAboutExpired(emailAndAds);
        }
        log.info("Search expired ads is finished. Letter was sent to {} addresses", emailAndAds.size());
    }

    //    @Scheduled(fixedRate = 8000)
    public void findExpiringAdvertisementsOld() {
        log.info("Search for expiring advertisement launched at {}", LocalDateTime.now());
        Map<String, List<AdvertisementDTO>> emailAndAds = new HashMap<>();
        List<AdvertisementDTO> advertisementsList = advertisementDAO.getExpiringAdvertisements();

        for (AdvertisementDTO advertisementDTO : advertisementsList) {
            if (!sentLettersExpiring.contains(advertisementDTO)) {
                String email = advertisementDTO.getOwner().getEmail();
                if (!emailAndAds.containsKey(email)) {
                    List<AdvertisementDTO> ads = new ArrayList<>();
                    ads.add(advertisementDTO);
                    emailAndAds.put(email, ads);
                } else {
                    List<AdvertisementDTO> ads = emailAndAds.get(email);
                    ads.add(advertisementDTO);
                }
                sentLettersExpiring.add(advertisementDTO);
            }
        }
        if (!emailAndAds.isEmpty()) {
            emailService.sendEmailAboutExpiring(emailAndAds);
        }
        log.info("Search is finished. Letter was sent to {} addresses", emailAndAds.size());
    }
}







