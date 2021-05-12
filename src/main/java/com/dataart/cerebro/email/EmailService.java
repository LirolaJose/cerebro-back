package com.dataart.cerebro.email;

import com.dataart.cerebro.domain.AdditionalService;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.repository.AdditionalServiceRepository;
import com.dataart.cerebro.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final UserInfoRepository userInfoRepository;
    private final AdditionalServiceRepository additionalServiceRepository;

    @Value("${spring.data.rest.page-param-name}")
    private String url;

    public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine, UserInfoRepository userInfoRepository, AdditionalServiceRepository additionalServiceRepository) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.userInfoRepository = userInfoRepository;
        this.additionalServiceRepository = additionalServiceRepository;
    }

    @Async
    public void sendEmailAboutPublication(Advertisement advertisement) {
        try {
            log.info("Sending email about publication is started");
            String template = "publicationTemplate";
            UserInfo userInfo = advertisement.getOwner();
            Map<String, Object> contextMap = new HashMap<>();
            contextMap.put("userInfo", userInfo);
            contextMap.put("advertisement", advertisement);
            contextMap.put("url", url);
            sendEmail(contextMap, template, "Information about the publication of advertisement");
            log.info("Email about publication has been sent to {}", userInfo.getEmail());
        } catch (Exception e) {
            log.error("Failed to send email about publication", e);
        }
    }

    @Async
    public void sendEmailAboutFinishingAdvertisement(Map<String, List<Advertisement>> emailAndAds, String action) {
        try {
            log.info("Sending email about {} is started", action);
            String template = "scheduleTemplate";
            for (Map.Entry<String, List<Advertisement>> entry : emailAndAds.entrySet()) {
                String email = entry.getKey();
                List<Advertisement> advertisementsList = entry.getValue();
                Map<String, Object> contextMap = new HashMap<>();
                contextMap.put("action", action);
                contextMap.put("userInfo", userInfoRepository.findUserInfoByEmail(email));
                contextMap.put("advertisementList", advertisementsList);
                contextMap.put("url", url);
                sendEmail(contextMap, template, "Information about the ending of advertisement");
                log.info("Email about expiring has been sent to {}", email);
            }
        } catch (Exception e) {
            log.error("Failed to send email about expiring", e);
        }
    }

    @Async
    public void sendEmailAboutPurchase(AdvertisementOrder order, UserInfo customer) {
        try {
            log.info("Sending email about purchase is started");
            String template = "orderTemplate";
            Advertisement advertisement = order.getAdvertisement();
            List<AdditionalService> servicesSet = additionalServiceRepository.findAdditionalServiceByOrderId(order.getId());
            Map<String, Object> contextMap = new HashMap<>();
            contextMap.put("action", "purchase");
            contextMap.put("userInfo", customer);
            contextMap.put("advertisement", advertisement);
            contextMap.put("additionalServices", servicesSet);
            contextMap.put("order", order);
            contextMap.put("url", url);
            sendEmail(contextMap, template, "Information about the purchase");
            log.info("Email about purchase has been sent to {}", customer.getEmail());
        } catch (Exception e) {
            log.error("Failed to send email about purchase", e);
        }
    }

    @Async
    public void sendEmailAboutSell(AdvertisementOrder order, UserInfo customer) {
        try {
            log.info("Sending email about sell is started");
            String template = "orderTemplate";
            Advertisement advertisement = order.getAdvertisement();
            List<AdditionalService> servicesSet = additionalServiceRepository.findAdditionalServiceByOrderId(order.getId());
            Map<String, Object> contextMap = new HashMap<>();
            contextMap.put("action", "sell");
            contextMap.put("userInfo", advertisement.getOwner());
            contextMap.put("customer", customer);
            contextMap.put("advertisement", advertisement);
            contextMap.put("additionalServices", servicesSet);
            contextMap.put("order", order);
            contextMap.put("url", url);
            sendEmail(contextMap, template, "Information about the sell");
            log.info("Email about sell has been sent to {}", advertisement.getOwner().getEmail());
        } catch (Exception e) {
            log.error("Failed to send email about sell", e);
        }
    }

//    @Async
//    public void sendEmailAboutOrder(AdvertisementOrder order, UserInfo customer, String action) {
//        executor.execute(() -> {
//            try{
//            log.info("Sending email about {} is started ", action);
//            String template = "orderTemplate";
//            Advertisement advertisement = order.getAdvertisement();
//            List<AdditionalService> servicesSet = additionalServiceRepository.findAdditionalServiceByOrderId(order.getId());
//            Map<String, Object> contextMap = new HashMap<>();
//            contextMap.put("action", action);
//            contextMap.put("userInfo", advertisement.getOwner());
//            contextMap.put("customer", customer);
//            contextMap.put("advertisement", advertisement);
//            contextMap.put("additionalServices", servicesSet);
//            contextMap.put("order", order);
//            contextMap.put("url", url);
//            sendEmail(contextMap, template, "Information about the order");
//            log.info("Email about {} has been sent to {}", action, customer.getEmail());
//            }catch (Exception e){
//                log.error("Failed to send email about {}", action, e);
//            }
//        });
//    }

    private void sendEmail(Map<String, Object> contextMap, String template, String subject) throws MessagingException {
        Context context = new Context();
        context.setVariables(contextMap);
        UserInfo userInfo = (UserInfo) contextMap.get("userInfo");

        String process = templateEngine.process("email.templates/" + template, context);
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(subject);
        helper.setText(process, true);
        helper.setTo(userInfo.getEmail());
        emailSender.send(mimeMessage);
    }

}


