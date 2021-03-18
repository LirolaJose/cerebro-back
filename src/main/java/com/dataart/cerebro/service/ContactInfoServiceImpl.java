package com.dataart.cerebro.service;

import com.dataart.cerebro.dao.ContactInfoDAO;
import com.dataart.cerebro.dto.ContactInfoDTO;
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
    public void addContactInfo(String name, String phone, String email) {
        contactInfoDAO.addContactInfo(name, phone, email);
    }

    @Override
    public List<ContactInfoDTO> getAllContactsInfo() {
        return contactInfoDAO.getAllContactsInfo();
    }
}
