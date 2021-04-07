package com.dataart.cerebro.email;

import com.dataart.cerebro.domain.AdOrderDTO;
import com.dataart.cerebro.domain.AdvertisementDTO;
import com.dataart.cerebro.domain.ContactInfoDTO;
import com.dataart.cerebro.domain.ServiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final Executor executor = Executors.newFixedThreadPool(5);

    @Value("${spring.data.rest.page-param-name}")
    private String url;

    private static final String SIGNATURE = "CEREBRO: \n +7 (222) 555-77-99 \n lirolaboard@gmail.com";

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendEmailAboutPublication(AdvertisementDTO advertisement, ContactInfoDTO contactInfo) {
        executor.execute(() -> {
            log.info("Sending email about publication is started at {}", LocalDateTime.now());
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            String email = contactInfo.getEmail();
            StringBuilder text = new StringBuilder();

            mailMessage.setTo(email);
            mailMessage.setSubject("Your advertisement is added");
            text.append(advertisement.getTitle()).append("\n")
                    .append(advertisement.getText()).append("\n \n")
                    .append(SIGNATURE);
            mailMessage.setText(text.toString());
            emailSender.send(mailMessage);
            log.info("Email about publication is sent to {} at {}", email, LocalDateTime.now());
        });
    }

    @Override
    public void sendEmailAboutExpiring(Map<String, List<AdvertisementDTO>> emailAndAds) {
        executor.execute(() -> {
            log.info("Sending email about expiring is started at {}", LocalDateTime.now());
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            emailAndAds.forEach(((email, advertisementsList) -> {
                ContactInfoDTO contactInfo = advertisementsList.get(0).getOwner();
                mailMessage.setTo(contactInfo.getEmail());
                mailMessage.setSubject("Advertisement will be closed soon");

                StringBuilder text = new StringBuilder();
                text.append("Dear ").append(contactInfo.getName()).append(",\n").append("your advertisement(s): \n");
                int number = 1;
                for (AdvertisementDTO advertisementDTO : advertisementsList) {
                    text.append(number++).append(". ").append(url).append(advertisementDTO.getId()).append(" will be closed ")
                            .append(advertisementDTO.getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))).append("\n \n");

                }
                text.append(SIGNATURE);
                mailMessage.setText(text.toString());
                emailSender.send(mailMessage);
                log.info("Email about expiring is sent to {} at {}", email, LocalDateTime.now());
            }
            ));
        });
    }

    @Override
    public void sendEmailAboutExpired(Map<String, List<AdvertisementDTO>> emailAndAds) {
        executor.execute(() -> {
            log.info("Sending email about expired is started at {}", LocalDateTime.now());
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            emailAndAds.forEach(((email, advertisementsList) -> {
                ContactInfoDTO contactInfo = advertisementsList.get(0).getOwner();
                mailMessage.setTo(contactInfo.getEmail());
                mailMessage.setSubject("Advertisement is closed");
                StringBuilder text = new StringBuilder();
                text.append("Dear ").append(contactInfo.getName()).append(",\n")
                        .append("your advertisement(s): \n");
                int number = 1;
                for (AdvertisementDTO advertisementDTO : advertisementsList) {
                    text.append(number++).append(". ").append(url).append(advertisementDTO.getId()).append(" is closed ").append("\n \n");
                }
                text.append(SIGNATURE);
                mailMessage.setText(text.toString());
                emailSender.send(mailMessage);
                log.info("Email about expired is sent to {} at {}", email, LocalDateTime.now());
            }));
        });
    }

    @Override
    public void sendEmailAboutPurchase(AdvertisementDTO advertisement, ContactInfoDTO customer, AdOrderDTO adOrder) {
        executor.execute(() -> {
            log.info("Sending email about purchase is started at {}", LocalDateTime.now());
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            Set<ServiceDTO> servicesSet = adOrder.getServicesSet();

            String services = servicesSet.stream()
                    .map(serviceDTO -> serviceDTO.getName() + ": " + serviceDTO.getPrice())
                    .collect(Collectors.joining(", "));

            mailMessage.setTo(customer.getEmail());
            mailMessage.setSubject("You have done a purchase on \"CEREBRO\"");
            StringBuilder text = new StringBuilder();

            text.append("Dear ").append(customer.getName()).append(",\n")
                    .append("You have done a purchase ").append(url).append(advertisement.getId()).append("\n")
                    .append("Total price: ").append(adOrder.getTotalPrice());
            if (!servicesSet.isEmpty()) {
                text.append(" (additional services: ").append(services).append(")").append("\n");
            }
            text.append("\n").append("If you haven't made this purchase, call or email us").append("\n \n")
                    .append(SIGNATURE);

            mailMessage.setText(text.toString());
            emailSender.send(mailMessage);
            log.info("Email about purchase is sent to {} at {}", customer.getEmail(), LocalDateTime.now());
        });
    }

    @Override
    public void sendEmailAboutSell(AdvertisementDTO advertisement, ContactInfoDTO customer, AdOrderDTO adOrder) {
        executor.execute(() -> {
            log.info("Sending email about sell is started at {}", LocalDateTime.now());
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            ContactInfoDTO seller = advertisement.getOwner();
            Set<ServiceDTO> servicesSet = adOrder.getServicesSet();

            String services = servicesSet.stream()
                    .map(serviceDTO -> serviceDTO.getName() + ": " + serviceDTO.getPrice())
                    .collect(Collectors.joining(", "));

            mailMessage.setTo(seller.getEmail());
            mailMessage.setSubject("You advertisement is closed on \"CEREBRO\"");
            StringBuilder text = new StringBuilder();

            text.append("Dear ").append(seller.getName()).append(",\n")
                    .append("Your advertisement ").append(url).append(advertisement.getId()).append(" is closed,").append("\n")
                    .append("because ").append(customer.getName()).append(" has made the purchase. \n")
                    .append("Total price: ").append(adOrder.getTotalPrice()).append("\n");
            if (!servicesSet.isEmpty()) {
                text.append("Additional services were ordered: ").append(services).append("\n");
            }
            text.append("Contact information: ").append("tel: ").append(customer.getPhone())
                    .append(", email: ").append(customer.getEmail()).append("\n \n")
                    .append(SIGNATURE);

            mailMessage.setText(text.toString());
            emailSender.send(mailMessage);
            log.info("Email about sell is sent to {} at {}", customer.getEmail(), LocalDateTime.now());
        });
    }
}
