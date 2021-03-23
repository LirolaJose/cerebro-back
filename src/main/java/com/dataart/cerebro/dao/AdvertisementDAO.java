package com.dataart.cerebro.dao;

import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface AdvertisementDAO {
    List<AdvertisementDTO> getAllAdvertisements();

    AdvertisementDTO getAdvertisementById(int id);

    byte[] getImageByAdvertisementId(int id);

    void addAdvertisement(String title, String text, Double price, String address, byte[] image, LocalDateTime publicationTime, LocalDateTime expiredTime,
                          int categoryId, int typeId, int statusId, ContactInfoDTO contactInfoDTO) throws SQLException;
}
