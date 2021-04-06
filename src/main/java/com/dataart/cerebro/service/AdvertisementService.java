package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.AdvertisementDTO;
import com.dataart.cerebro.domain.ContactInfoDTO;

import java.util.List;

public interface AdvertisementService {
    List<AdvertisementDTO> getAllActiveAdvertisements();

    AdvertisementDTO getAdvertisementById(Integer id);

    void addAdvertisement(AdvertisementDTO advertisement, ContactInfoDTO contactInfo, byte[] image);
}
