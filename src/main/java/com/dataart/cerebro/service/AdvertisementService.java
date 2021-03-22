package com.dataart.cerebro.service;

import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;

import java.sql.SQLException;
import java.util.List;

public interface AdvertisementService {
    List<AdvertisementDTO> getAllAdvertisements();

    AdvertisementDTO getAdvertisementById(Integer id);

    void addAdvertisement(AdvertisementDTO advertisementDTO, ContactInfoDTO contactInfoDTO, byte[] image) throws SQLException;
}
