package com.dataart.cerebro.service;

import com.dataart.cerebro.dao.ContactInfoDAO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.exception.ContactInfoNullPointerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContactInfoServiceImpl implements ContactInfoService {
    final ContactInfoDAO contactInfoDAO;

    public ContactInfoServiceImpl(ContactInfoDAO contactInfoDAO) {
        this.contactInfoDAO = contactInfoDAO;
    }

    @Override
    public ContactInfoDTO addContactInfo(ContactInfoDTO contactInfoDTO) {
        log.info("Creating a new contact info: name - {}, phone: {}, email: {}",
                contactInfoDTO.getName(), contactInfoDTO.getPhone(), contactInfoDTO.getEmail());
        return contactInfoDAO.addContactInfo(contactInfoDTO.getName(), contactInfoDTO.getPhone(), contactInfoDTO.getEmail());
    }

    @Override
    public List<ContactInfoDTO> getAllContactsInfo() {
        return contactInfoDAO.getAllContactsInfo();
    }
}
