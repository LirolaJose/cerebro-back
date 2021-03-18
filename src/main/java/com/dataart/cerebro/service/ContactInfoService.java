package com.dataart.cerebro.service;

import com.dataart.cerebro.dto.ContactInfoDTO;

import java.util.List;

public interface ContactInfoService {
    void addContactInfo(String name, String phone, String email);
    List<ContactInfoDTO> getAllContactsInfo();
}
