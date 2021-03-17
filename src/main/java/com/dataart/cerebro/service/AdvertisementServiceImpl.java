package com.dataart.cerebro.service;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.exception.AdvertisementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AdvertisementServiceImpl implements AdvertisementService {
    final
    AdvertisementDAO advertisementDAO;

    public AdvertisementServiceImpl(AdvertisementDAO advertisementDAO) {
        this.advertisementDAO = advertisementDAO;
    }

    @Override
    public List<AdvertisementDTO> getAllAdvertisements() {
        return advertisementDAO.getAllAdvertisements();
    }

    @Override
    public AdvertisementDTO getAdvertisementById(int id) {
        AdvertisementDTO advertisementById = advertisementDAO.getAdvertisementById(id);
        if (advertisementById == null) {
            log.info("Bad request for ID({}), this id doesn't exist", id);
            throw new AdvertisementNotFoundException(id);
        }
        return advertisementById;
    }

    @Override
    public void addAdvertisement(String title, String text, Double price, String address, Integer categoryId, Integer typeId, Integer statusId) {
        advertisementDAO.addAdvertisement(title, text, price, address, categoryId, typeId, statusId);
    }
}
