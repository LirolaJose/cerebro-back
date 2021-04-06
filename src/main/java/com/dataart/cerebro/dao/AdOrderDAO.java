package com.dataart.cerebro.dao;

import com.dataart.cerebro.domain.AdOrderDTO;
import com.dataart.cerebro.domain.AdvertisementDTO;
import com.dataart.cerebro.domain.ContactInfoDTO;

import java.time.LocalDateTime;

public interface AdOrderDAO {
    AdOrderDTO addAdOrder(AdOrderDTO adOrder, LocalDateTime orderTime, AdvertisementDTO advertisement, ContactInfoDTO customerInfo);
}
