package com.dataart.cerebro.email;

import com.dataart.cerebro.domain.AdditionalService;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final UserInfoRepository userInfoRepository;

    private final Executor executor = Executors.newFixedThreadPool(10);

    @Value("${spring.data.rest.page-param-name}")
    private String url;

    public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine, UserInfoRepository userInfoRepository) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.userInfoRepository = userInfoRepository;
    }

    public void sendEmailAboutPublication(Advertisement advertisement) {
        executor.execute(() -> {
            log.info("Sending email about publication is started at {}", LocalDateTime.now());
            String template = "publicationTemplate";
            UserInfo userInfo = advertisement.getOwner();
            Map<String, Object> contextMap = new HashMap<>();
            contextMap.put("userInfo", userInfo);
            contextMap.put("advertisement", advertisement);
            contextMap.put("url", url);
            sendEmail(contextMap, template);
            log.info("Email about publication has been sent to {} at {}", userInfo.getEmail(), LocalDateTime.now());
        });
    }

    public void sendEmailAboutExpiring(Map<String, List<Advertisement>> emailAndAds) {
        executor.execute(() -> {
            log.info("Sending email about expiring is started at {}", LocalDateTime.now());
            String template = "scheduleTemplate";
            emailAndAds.forEach(((email, advertisementsList) -> {
                Map<String, Object> contextMap = new HashMap<>();
                contextMap.put("action", "soon");
                contextMap.put("userInfo", userInfoRepository.findUserInfoByEmail(email));
                contextMap.put("advertisementList", advertisementsList);
                contextMap.put("url", url);
                sendEmail(contextMap, template);
                log.info("Email about expiring has been sent to {} at {}", email, LocalDateTime.now());
            }));
        });
    }

    public void sendEmailAboutExpired(Map<String, List<Advertisement>> emailAndAds) {
        executor.execute(() -> {
            log.info("Sending email about expired is started at {}", LocalDateTime.now());
            String template = "scheduleTemplate";
            emailAndAds.forEach(((email, advertisementsList) -> {
                Map<String, Object> contextMap = new HashMap<>();
                contextMap.put("action", "expired");
                contextMap.put("userInfo", userInfoRepository.findUserInfoByEmail(email));
                contextMap.put("advertisementList", advertisementsList);
                contextMap.put("url", url);
                sendEmail(contextMap, template);
                log.info("Email about expired has been sent to {} at {}", email, LocalDateTime.now());
            }));
        });
    }


    public void sendEmailAboutPurchase(AdvertisementOrder order, UserInfo customer) {
        executor.execute(() -> {
            log.info("Sending email about purchase is started at {}", LocalDateTime.now());
            String template = "orderTemplate";
            Advertisement advertisement = order.getAdvertisement();
            Set<AdditionalService> servicesSet = order.getAdditionalServices();
            Map<String, Object> contextMap = new HashMap<>();
            contextMap.put("action", "purchase");
            contextMap.put("userInfo", customer);
            contextMap.put("advertisement", advertisement);
            contextMap.put("additionalServices", servicesSet);
            contextMap.put("order", order);
            contextMap.put("url", url);
            sendEmail(contextMap, template);
            log.info("Email about purchase is sent to {} at {}", customer.getEmail(), LocalDateTime.now());

        });
    }


    public void sendEmailAboutSell(AdvertisementOrder order, UserInfo customer) {
        executor.execute(() -> {
            log.info("Sending email about sell is started at {}", LocalDateTime.now());
            String template = "orderTemplate";
            Advertisement advertisement = order.getAdvertisement();
            Set<AdditionalService> servicesSet = order.getAdditionalServices();
            Map<String, Object> contextMap = new HashMap<>();
            contextMap.put("action", "sell");
            contextMap.put("userInfo", advertisement.getOwner());
            contextMap.put("customer", customer);
            contextMap.put("advertisement", advertisement);
            contextMap.put("additionalServices", servicesSet);
            contextMap.put("order", order);
            contextMap.put("url", url);
            sendEmail(contextMap, template);
            log.info("Email about sell is sent to {} at {}", advertisement.getOwner().getEmail(), LocalDateTime.now());

        });
    }


    private void sendEmail(Map<String, Object> contextMap, String template) {
//        executor.execute(() -> {
        Context context = new Context();
        context.setVariables(contextMap);
        UserInfo userInfo = (UserInfo) contextMap.get("userInfo");

        String process = templateEngine.process("email.templates/" + template, context);
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setSubject("CEREBRO");
            helper.setText(process, true);
            helper.setTo(userInfo.getEmail());
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
//        });
    }

}


