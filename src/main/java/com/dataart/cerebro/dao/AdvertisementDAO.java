package com.dataart.cerebro.dao;

import com.dataart.cerebro.dto.AdvertisementDTO;

import java.sql.Blob;
import java.util.List;

public interface AdvertisementDAO {
    List<AdvertisementDTO> getAllAdvertisements ();
    AdvertisementDTO getAdvertisementById(int id);
    byte[] getImageByAdvertisementId(int id);
}
