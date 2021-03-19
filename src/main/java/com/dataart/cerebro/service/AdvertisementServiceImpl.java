package com.dataart.cerebro.service;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.exception.AdvertisementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AdvertisementServiceImpl implements AdvertisementService {
    final AdvertisementDAO advertisementDAO;
    final CategoryService categoryService;
    final ContactInfoService contactInfoService;

    public AdvertisementServiceImpl(AdvertisementDAO advertisementDAO, CategoryService categoryService, ContactInfoService contactInfoService) {
        this.advertisementDAO = advertisementDAO;
        this.categoryService = categoryService;
        this.contactInfoService = contactInfoService;
    }

    @Override
    public List<AdvertisementDTO> getAllAdvertisements() {
        return advertisementDAO.getAllAdvertisements();
    }

    @Override
    public AdvertisementDTO getAdvertisementById(Integer id) {
        AdvertisementDTO advertisementById = advertisementDAO.getAdvertisementById(id);
        if (advertisementById == null) {
            log.info("Bad request for ID({}), this id doesn't exist", id);
            throw new AdvertisementNotFoundException(id);
        }
        return advertisementById;
    }

    @Override
    public void addAdvertisement(AdvertisementDTO advertisementDTO, ContactInfoDTO contactInfoDTO) {
//        CategoryDTO categoryDTO = categoryService.getCategoryById(advertisementDTO.getCategoryDTO().getId());
        contactInfoDTO = contactInfoService.addContactInfo(contactInfoDTO);
        LocalDateTime publicationTime = LocalDateTime.now();
        LocalDateTime endTime = publicationTime.plusDays(7);

        advertisementDAO.addAdvertisement(advertisementDTO.getTitle(), advertisementDTO.getText(), advertisementDTO.getPrice(),
                advertisementDTO.getAddress(), publicationTime, endTime, advertisementDTO.getCategoryDTO().getId(),
                advertisementDTO.getTypeDTO().getId(), advertisementDTO.getStatusDTO().getId(), contactInfoDTO.getId());
    }
}
