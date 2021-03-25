package com.dataart.cerebro.email;

import com.dataart.cerebro.dto.AdvertisementDTO;

import java.util.List;
import java.util.Map;

public interface EmailService {
    void sendEmailAboutPublication(String title, String text, String email);

    void sendEmailAboutExpiring(Map<String, List<AdvertisementDTO>> emailAndAds);

    void sendEmailAboutExpired(Map<String, List<AdvertisementDTO>> emailAndAds);
}
