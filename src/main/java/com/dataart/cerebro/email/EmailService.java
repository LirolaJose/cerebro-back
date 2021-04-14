package com.dataart.cerebro.email;

import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.ContactInfo;

import java.util.List;
import java.util.Map;

public interface EmailService {
    void sendEmailAboutPublication(Advertisement advertisement, ContactInfo contactInfo);

    void sendEmailAboutExpiring(Map<String, List<Advertisement>> emailAndAds);

    void sendEmailAboutExpired(Map<String, List<Advertisement>> emailAndAds);

    void sendEmailAboutPurchase(Advertisement advertisement, ContactInfo customer, AdvertisementOrder adOrder);

    void sendEmailAboutSell(Advertisement advertisement, ContactInfo customer, AdvertisementOrder adOrder);
}
