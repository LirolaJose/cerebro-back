package com.dataart.cerebro.dao;

import com.dataart.cerebro.domain.*;

import java.time.LocalDateTime;
import java.util.List;

public interface AdvertisementDAO {
    List<AdvertisementDTO> getAllActiveAdvertisements();

    AdvertisementDTO getAdvertisementById(int id);

    byte[] getImageByAdvertisementId(int id);

    void addAdvertisement(String title, String text, Double price, String address, byte[] image, LocalDateTime publicationTime, LocalDateTime expiredTime,
                          CategoryDTO category, Type type, Status status, ContactInfoDTO contactInfo);

    List<AdvertisementDTO> getExpiringAdvertisements();

    List<AdvertisementDTO> getExpiredAdvertisements();

    void updateAdvertisementStatus(AdvertisementDTO advertisement, Status status);
}
