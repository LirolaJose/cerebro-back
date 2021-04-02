package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.AdOrderDTO;
import com.dataart.cerebro.domain.AdvertisementDTO;
import com.dataart.cerebro.domain.ContactInfoDTO;

public interface AdOrderService {
    void addAdOrder(AdOrderDTO adOrder, AdvertisementDTO advertisement, ContactInfoDTO customerInfo);
}
