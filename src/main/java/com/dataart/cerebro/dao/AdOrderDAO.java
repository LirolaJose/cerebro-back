package com.dataart.cerebro.dao;

import com.dataart.cerebro.dto.AdOrderDTO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;

import java.time.LocalDateTime;

public interface AdOrderDAO {
    void addAdOrder(AdOrderDTO adOrderDTO, LocalDateTime orderTime, AdvertisementDTO advertisementDTO, ContactInfoDTO customerInfo);
}
