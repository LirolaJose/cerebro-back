package com.dataart.cerebro.service.impl;

import com.dataart.cerebro.dao.ContactInfoDAO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.service.ContactInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoDAO contactInfoDAO;

    public ContactInfoServiceImpl(ContactInfoDAO contactInfoDAO) {
        this.contactInfoDAO = contactInfoDAO;
    }

    @Override
    public List<ContactInfoDTO> getAllContactsInfo() {
        return contactInfoDAO.getAllContactsInfo();
    }
}
