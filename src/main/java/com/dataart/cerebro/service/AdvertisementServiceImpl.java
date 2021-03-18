package com.dataart.cerebro.service;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.CategoryDTO;
import com.dataart.cerebro.exception.AdvertisementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AdvertisementServiceImpl implements AdvertisementService {
    final AdvertisementDAO advertisementDAO;
    final CategoryService categoryService;

    public AdvertisementServiceImpl(AdvertisementDAO advertisementDAO, CategoryService categoryService) {
        this.advertisementDAO = advertisementDAO;
        this.categoryService = categoryService;
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
    public void addAdvertisement(String title, String text, Double price, String address,
                                 int categoryId, int typeId, int statusId) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        LocalDateTime publicationTime = LocalDateTime.now();

        LocalDateTime endTime = publicationTime.plusDays(7);

        advertisementDAO.addAdvertisement(title, text, price, address, publicationTime, endTime, categoryDTO.getId(), typeId, statusId);
    }
}
