package com.dataart.cerebro.service;

import com.dataart.cerebro.dto.AdOrderDTO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;

import java.time.LocalDateTime;

public interface AdOrderService {
    void adAdOrder(AdOrderDTO adOrder, AdvertisementDTO advertisement, ContactInfoDTO customerInfo);
}
