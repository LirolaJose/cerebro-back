package com.dataart.cerebro.email;

import com.dataart.cerebro.dto.AdOrderDTO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;

import java.util.List;
import java.util.Map;

public interface EmailService {
    void sendEmailAboutPublication(String title, String text, String email);

    void sendEmailAboutExpiring(Map<String, List<AdvertisementDTO>> emailAndAds);

    void sendEmailAboutExpired(Map<String, List<AdvertisementDTO>> emailAndAds);

    void sendEmailAboutPurchase(AdvertisementDTO advertisementDTO, ContactInfoDTO customer, AdOrderDTO adOrderDTO);

    void sendEmailAboutSell(AdvertisementDTO advertisementDTO, ContactInfoDTO customer, AdOrderDTO adOrderDTO);
}
