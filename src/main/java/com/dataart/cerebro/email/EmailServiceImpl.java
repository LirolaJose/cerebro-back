package com.dataart.cerebro.email;

import com.dataart.cerebro.dto.AdOrderDTO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.dto.ServiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
            mailMessage.setTo(contactInfoDTO.getEmail());
            mailMessage.setSubject("Advertisement will be closed soon");
            StringBuilder text = new StringBuilder();
            text.append("Dear ").append(contactInfoDTO.getName()).append(",\n").append("your advertisement(s): \n");
            int number = 1;
            for (AdvertisementDTO advertisementDTO : advertisementsList) {
                text.append(number++).append(". ").append(url).append(advertisementDTO.getId()).append(" will be closed ")
                        .append(advertisementDTO.getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))).append("\n");
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
            mailMessage.setTo(contactInfoDTO.getEmail());
            mailMessage.setSubject("Advertisement is closed");
            StringBuilder text = new StringBuilder();
            text.append("Dear ").append(contactInfoDTO.getName()).append(",\n")
                    .append("your advertisement(s): \n");
            int number = 1;
            for (AdvertisementDTO advertisementDTO : advertisementsList) {
                text.append(number++).append(". ").append(url).append(advertisementDTO.getId()).append(" is closed ").append("\n");
            }
            mailMessage.setText(text.toString());
            emailSender.send(mailMessage);
            log.info("Email is sent to {}", email);
        }));
    }

    @Override
    public void sendEmailAboutPurchase(AdvertisementDTO advertisementDTO, ContactInfoDTO customer, AdOrderDTO adOrderDTO) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        Set<ServiceDTO> servicesSet = adOrderDTO.getServicesSet();
        String services = servicesSet.stream()
                .map(serviceDTO -> serviceDTO.getName() + ": " + serviceDTO.getPrice())
                .collect(Collectors.joining(", "));

        mailMessage.setTo(customer.getEmail());
        mailMessage.setSubject("You have done a purchase on \"CEREBRO\"");
        StringBuilder text = new StringBuilder();

        text.append("Dear ").append(customer.getName()).append(",\n")
                .append("You have done a purchase ").append(url).append(advertisementDTO.getId()).append("\n")
                .append("Total price: ").append(adOrderDTO.getTotalPrice());
        if (!servicesSet.isEmpty()) {
            text.append(" (additional services: ").append(services).append(")").append("\n");
        }
        text.append("\n \n").append("CEREBRO:").append("\n")
                .append("if you haven't made this purchase, call or email us").append("\n \n")
                .append("+7 (222) 555-77-99").append("\n")
                .append("lirolaboard@gmail.com");
        mailMessage.setText(text.toString());
        emailSender.send(mailMessage);
        log.info("Email is sent to {}", customer.getEmail());
    }

    @Override
    public void sendEmailAboutSell(AdvertisementDTO advertisementDTO, ContactInfoDTO customer, AdOrderDTO adOrderDTO) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        ContactInfoDTO seller = advertisementDTO.getOwner();

        Set<ServiceDTO> servicesSet = adOrderDTO.getServicesSet();
        String services = servicesSet.stream()
                .map(serviceDTO -> serviceDTO.getName() + ": " + serviceDTO.getPrice())
                .collect(Collectors.joining(", "));

        mailMessage.setTo(seller.getEmail());
        mailMessage.setSubject("You advertisement is closed on \"CEREBRO\"");
        StringBuilder text = new StringBuilder();

        text.append("Dear ").append(seller.getName()).append(",\n")
                .append("Your advertisement ").append(url).append(advertisementDTO.getId()).append(" is closed,").append("\n")
                .append("because ").append(customer.getName()).append(" has made the purchase. \n")
                .append("Total price: ").append(adOrderDTO.getTotalPrice()).append("\n");
        if(!servicesSet.isEmpty()){
            text.append("Additional services were ordered: ").append(services).append("\n");
        }
        text.append("Contact information: ").append("tel: ").append(customer.getPhone())
                .append(", email: ").append(customer.getEmail()).append("\n")
                .append("\n \n").append("CEREBRO:").append("\n")
                .append("+7 (222) 555-77-99").append("\n")
                .append("lirolaboard@gmail.com");
        mailMessage.setText(text.toString());
        emailSender.send(mailMessage);
        log.info("Email is sent to {}", customer.getEmail());
    }
}