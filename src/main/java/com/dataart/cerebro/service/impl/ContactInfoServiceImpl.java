package com.dataart.cerebro.service.impl;

import com.dataart.cerebro.dao.ContactInfoRepository;
import com.dataart.cerebro.domain.ContactInfo;
import com.dataart.cerebro.service.ContactInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;

    public ContactInfoServiceImpl(ContactInfoRepository contactInfoRepository) {
        this.contactInfoRepository = contactInfoRepository;
    }

    @Override
    public List<ContactInfo> getAllContactsInfo() {
        return contactInfoRepository.getAllContactsInfo();
    }
}
