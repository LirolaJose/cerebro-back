package com.dataart.cerebro.service.impl;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.dto.StatusDTO;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.exception.AdvertisementNotFoundException;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.CategoryService;
import com.dataart.cerebro.service.ContactInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementDAO advertisementDAO;
    private final CategoryService categoryService;
    private final ContactInfoService contactInfoService;
    private final EmailService emailService;

    public AdvertisementServiceImpl(AdvertisementDAO advertisementDAO, CategoryService categoryService, ContactInfoService contactInfoService, EmailService emailService) {
        this.advertisementDAO = advertisementDAO;
        this.categoryService = categoryService;
        this.contactInfoService = contactInfoService;
        this.emailService = emailService;
    }

    @Override
    public List<AdvertisementDTO> getAllActiveAdvertisements() {
        return advertisementDAO.getAllActiveAdvertisements();
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
    public void addAdvertisement(AdvertisementDTO advertisement, ContactInfoDTO contactInfo, byte[] image) {
        LocalDateTime publicationTime = LocalDateTime.now();
        LocalDateTime endTime = publicationTime.plusDays(7);
        StatusDTO status = StatusDTO.ACTIVE;

        advertisementDAO.addAdvertisement(advertisement.getTitle(), advertisement.getText(), advertisement.getPrice(),
                advertisement.getAddress(), image, publicationTime, endTime, advertisement.getCategory().getId(),
                advertisement.getType().getId(), status.getId(), contactInfo);

        emailService.sendEmailAboutPublication(advertisement.getTitle(), advertisement.getText(), contactInfo.getEmail());
    }
}
