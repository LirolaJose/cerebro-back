package com.dataart.cerebro.service.impl;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.domain.AdvertisementDTO;
import com.dataart.cerebro.domain.ContactInfoDTO;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementDAO advertisementDAO;
    private final EmailService emailService;

    public AdvertisementServiceImpl(AdvertisementDAO advertisementDAO, EmailService emailService) {
        this.advertisementDAO = advertisementDAO;
        this.emailService = emailService;
    }

    @Override
    public List<AdvertisementDTO> getAllActiveAdvertisements() {
        return advertisementDAO.getAllActiveAdvertisements();
    }

    @Override
    public AdvertisementDTO getAdvertisementById(Integer id) {
        AdvertisementDTO advertisement = advertisementDAO.getAdvertisementById(id);
        if (advertisement == null) {
            log.info("Bad request for ID({}), this id doesn't exist", id);
            throw new NotFoundException("Advertisement", id);
        }
        return advertisement;
    }

    @Override
    public void addAdvertisement(AdvertisementDTO advertisement, ContactInfoDTO contactInfo, byte[] image) {
        LocalDateTime publicationTime = LocalDateTime.now();
        LocalDateTime endTime = publicationTime.plusDays(7);

        advertisementDAO.addAdvertisement(advertisement.getTitle(), advertisement.getText(), advertisement.getPrice(),
                advertisement.getAddress(), image, publicationTime, endTime, advertisement.getCategory(),
                advertisement.getType(), Status.ACTIVE, contactInfo);

        emailService.sendEmailAboutPublication(advertisement, contactInfo);
    }
}
