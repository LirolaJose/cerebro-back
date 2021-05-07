package com.dataart.cerebro.email;

import com.dataart.cerebro.domain.AdditionalService;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.exception.DataProcessingException;
import com.dataart.cerebro.repository.AdditionalServiceRepository;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final UserInfoRepository userInfoRepository;
    private final AdditionalServiceRepository additionalServiceRepository;

    private final Executor executor = Executors.newFixedThreadPool(10);

    @Value("${spring.data.rest.page-param-name}")
    private String url;

    public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine, UserInfoRepository userInfoRepository, AdditionalServiceRepository additionalServiceRepository) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.userInfoRepository = userInfoRepository;
        this.additionalServiceRepository = additionalServiceRepository;
    }

    public void sendEmailAboutPublication(Advertisement advertisement) {
        executor.execute(() -> {
            try {
                log.info("Sending email about publication is started at {}", LocalDateTime.now());
                String template = "publicationTemplate";
                UserInfo userInfo = advertisement.getOwner();
                Map<String, Object> contextMap = new HashMap<>();
                contextMap.put("userInfo", userInfo);
                contextMap.put("advertisement", advertisement);
                contextMap.put("url", url);
                sendEmail(contextMap, template, "Information about the publication of advertisement");
                log.info("Email about publication has been sent to {} at {}", userInfo.getEmail(), LocalDateTime.now());
            }catch (Exception e){
                log.error("Failed to send email about publication", e);
                throw new DataProcessingException("Error during email sending about publication", e);
            }
        });
    }

    public void sendEmailAboutFinishingAdvertisement(Map<String, List<Advertisement>> emailAndAds, String action) {
        executor.execute(() -> {
            try {
                log.info("Sending email about expiring is started at {}", LocalDateTime.now());
                String template = "scheduleTemplate";
                emailAndAds.forEach(((email, advertisementsList) -> {
                    Map<String, Object> contextMap = new HashMap<>();
                    contextMap.put("action", action);
                    contextMap.put("userInfo", userInfoRepository.findUserInfoByEmail(email));
                    contextMap.put("advertisementList", advertisementsList);
                    contextMap.put("url", url);
                    sendEmail(contextMap, template, "Information about the ending of advertisement");
                    log.info("Email about expiring has been sent to {} at {}", email, LocalDateTime.now());
                }));
            }catch (Exception e){
                log.error("Failed to send email about expiring", e);
                throw new DataProcessingException("Error during email sending about expiring", e);
            }
        });
    }
    
    public void sendEmailAboutOrder(AdvertisementOrder order, UserInfo customer, String action) {
        executor.execute(() -> {
            try{
            log.info("Sending email about purchase is started at {}", LocalDateTime.now());
            String template = "orderTemplate";
            Advertisement advertisement = order.getAdvertisement();
            List<AdditionalService> servicesSet = additionalServiceRepository.findAdditionalServiceByOrderId(order.getId());
            Map<String, Object> contextMap = new HashMap<>();
            contextMap.put("action", action);
            contextMap.put("userInfo", customer);
            contextMap.put("advertisement", advertisement);
            contextMap.put("additionalServices", servicesSet);
            contextMap.put("order", order);
            contextMap.put("url", url);
            sendEmail(contextMap, template, "Information about the order");
            log.info("Email about purchase is sent to {} at {}", customer.getEmail(), LocalDateTime.now());
            }catch (Exception e){
                log.error("Failed to send email about purchase", e);
                throw new DataProcessingException("Error during email sending about purchase", e);
            }
        });
    }

    private void sendEmail(Map<String, Object> contextMap, String template, String subject) {
        Context context = new Context();
        context.setVariables(contextMap);
        UserInfo userInfo = (UserInfo) contextMap.get("userInfo");

        String process = templateEngine.process("email.templates/" + template, context);
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setSubject(subject);
            helper.setText(process, true);
            helper.setTo(userInfo.getEmail());
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}


