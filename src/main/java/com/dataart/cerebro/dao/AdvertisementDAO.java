package com.dataart.cerebro.dao;

import com.dataart.cerebro.dto.AdvertisementDTO;

import java.util.List;

public interface AdvertisementDAO {
    List<AdvertisementDTO> getAllAdvertisements();

    AdvertisementDTO getAdvertisementById(int id);

    byte[] getImageByAdvertisementId(int id);

    void addAdvertisement(String title, String text, Double price, String address, int typeId, int statusId); //int categoryId
}
