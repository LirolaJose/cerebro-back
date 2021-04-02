package com.dataart.cerebro.dao;

import com.dataart.cerebro.dto.AdOrderDTO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;

import java.time.LocalDateTime;

public interface AdOrderDAO {
    AdOrderDTO addAdOrder(AdOrderDTO adOrder, LocalDateTime orderTime, AdvertisementDTO advertisement, ContactInfoDTO customerInfo);

    AdOrderDTO getAdOrderById(int id);
}
