package com.dataart.cerebro.email;

import com.dataart.cerebro.domain.AdOrderDTO;
import com.dataart.cerebro.domain.AdvertisementDTO;
import com.dataart.cerebro.domain.ContactInfoDTO;

import java.util.List;
import java.util.Map;

public interface EmailService {
    void sendEmailAboutPublication(AdvertisementDTO advertisement, ContactInfoDTO contactInfo);

    void sendEmailAboutExpiring(Map<String, List<AdvertisementDTO>> emailAndAds);

    void sendEmailAboutExpired(Map<String, List<AdvertisementDTO>> emailAndAds);

    void sendEmailAboutPurchase(AdvertisementDTO advertisement, ContactInfoDTO customer, AdOrderDTO adOrder);

    void sendEmailAboutSell(AdvertisementDTO advertisement, ContactInfoDTO customer, AdOrderDTO adOrder);
}
