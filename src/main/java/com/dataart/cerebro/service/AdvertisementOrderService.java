package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.AdvertisementOrder;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.ContactInfo;

public interface AdvertisementOrderService {
    void addAdOrder(AdvertisementOrder adOrder, Advertisement advertisement, ContactInfo customerInfo);
}
