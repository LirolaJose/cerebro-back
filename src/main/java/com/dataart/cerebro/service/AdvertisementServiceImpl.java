package com.dataart.cerebro.service;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.dto.StatusDTO;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.exception.AdvertisementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AdvertisementServiceImpl implements AdvertisementService {
    final AdvertisementDAO advertisementDAO;
    final CategoryService categoryService;
    final ContactInfoService contactInfoService;
    final EmailService emailService;

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
    public void addAdvertisement(AdvertisementDTO advertisementDTO, ContactInfoDTO contactInfoDTO, byte[] image) throws SQLException {
        LocalDateTime publicationTime = LocalDateTime.now();
        LocalDateTime endTime = publicationTime.plusDays(7);
        StatusDTO status = StatusDTO.ACTIVE;
        advertisementDAO.addAdvertisement(advertisementDTO.getTitle(), advertisementDTO.getText(), advertisementDTO.getPrice(),
                advertisementDTO.getAddress(), image, publicationTime, endTime, advertisementDTO.getCategoryDTO().getId(),
                advertisementDTO.getTypeDTO().getId(), status.getId(), contactInfoDTO);
        emailService.sendEmailAboutPublication(advertisementDTO.getTitle(), advertisementDTO.getText(), contactInfoDTO.getEmail());
    }
}
