package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.ContactInfo;
import com.dataart.cerebro.domain.Status;

import java.util.List;

public interface AdvertisementService {
    List<Advertisement> findAllAdvertisements();

    List<Advertisement> findActiveAdvertisements();

    List<Advertisement> getAllActiveAdvertisements();

    Advertisement getAdvertisementById(Long id);

    void addAdvertisement(Advertisement advertisement, ContactInfo contactInfo, byte[] image);
}
