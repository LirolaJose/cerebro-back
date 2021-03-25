package com.dataart.cerebro.email;

import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Value("${spring.data.rest.page-param-name}")
    private String url;


    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendEmailAboutPublication(String title, String text, String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Your advertisement is added");
        mailMessage.setText(title + "\n" + text);
        emailSender.send(mailMessage);
    }

    @Override
    public void sendEmailAboutExpiring(Map<String, List<AdvertisementDTO>> emailAndAds) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        emailAndAds.forEach(((email, advertisementsList) -> {
            ContactInfoDTO contactInfoDTO = advertisementsList.get(0).getOwner();
            mailMessage.setTo("lirolaboard@gmail.com"); //contactInfo.getEmail()
            mailMessage.setSubject("Advertisement will be closed soon");
            StringBuilder text = new StringBuilder();
            text.append("Dear ").append(contactInfoDTO.getName()).append(",\n").append("your advertisement(s): \n");
            int number = 1;
            for (AdvertisementDTO advertisementDTO : advertisementsList) {
                text.append(number).append(". ").append(url).append(advertisementDTO.getId()).append(" will be closed ")
                        .append(advertisementDTO.getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))).append("\n");
                number++;
            }
            mailMessage.setText(text.toString());
            emailSender.send(mailMessage);
            log.info("Email is sent to {}", email);
        }
        ));
    }

    @Override
    public void sendEmailAboutExpired(Map<String, List<AdvertisementDTO>> emailAndAds) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        emailAndAds.forEach(((email, advertisementsList) -> {
            ContactInfoDTO contactInfoDTO = advertisementsList.get(0).getOwner();
            mailMessage.setTo("lirolaboard@gmail.com"); //contactInfo.getEmail()
            mailMessage.setSubject("Advertisement is closed");
            StringBuilder text = new StringBuilder();
            text.append("Dear ").append(contactInfoDTO.getName()).append(",\n").append("your advertisement(s): \n");
            int number = 1;
            for (AdvertisementDTO advertisementDTO : advertisementsList) {
                text.append(number).append(". ").append(url).append(advertisementDTO.getId()).append(" is closed ").append("\n");
                number++;
            }
            mailMessage.setText(text.toString());
            emailSender.send(mailMessage);
            log.info("Email is sent to {}", email);
        }));
    }
}