package com.dataart.cerebro.service;

import com.dataart.cerebro.repository.ContactInfoRepository;
import com.dataart.cerebro.domain.ContactInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;

    public ContactInfoService(ContactInfoRepository contactInfoRepository) {
        this.contactInfoRepository = contactInfoRepository;
    }

    public List<ContactInfo> getAllContactsInfo() {
        return contactInfoRepository.getAllContactsInfo();
    }
}
