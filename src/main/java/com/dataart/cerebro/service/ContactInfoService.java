package com.dataart.cerebro.service;

import com.dataart.cerebro.dto.ContactInfoDTO;

import java.util.List;

public interface ContactInfoService {
    ContactInfoDTO addContactInfo(ContactInfoDTO contactInfoDTO);
    List<ContactInfoDTO> getAllContactsInfo();
}
