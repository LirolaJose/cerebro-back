package com.dataart.cerebro.service;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    final
    AdvertisementDAO advertisementDAO;

    public AdvertisementServiceImpl(AdvertisementDAO advertisementDAO) {
        this.advertisementDAO = advertisementDAO;
    }

    @Override
    public List<AdvertisementDTO> getAllAdvertisement() {
        return advertisementDAO.getAllAdvertisements();
    }
}
