package com.dataart.cerebro.service;

import com.dataart.cerebro.dto.AdvertisementDTO;

import java.util.Date;
import java.util.List;

public interface AdvertisementService {
    List<AdvertisementDTO> getAllAdvertisements();
    AdvertisementDTO getAdvertisementById(int id);
    void addAdvertisement (String title, String text, Double price, String address, Integer categoryId, Integer typeId, Integer statusId);
}
