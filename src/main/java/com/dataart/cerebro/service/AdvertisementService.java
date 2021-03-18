package com.dataart.cerebro.service;

import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.CategoryDTO;
import com.dataart.cerebro.dto.StatusDTO;
import com.dataart.cerebro.dto.TypeDTO;

import java.util.List;

public interface AdvertisementService {
    List<AdvertisementDTO> getAllAdvertisements();

    AdvertisementDTO getAdvertisementById(int id);

    void addAdvertisement(String title, String text, Double price, String address,
                          int categoryId, int typeId, int statusId);
}
