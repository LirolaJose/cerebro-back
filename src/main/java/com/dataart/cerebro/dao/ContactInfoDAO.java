package com.dataart.cerebro.dao;

import com.dataart.cerebro.dto.ContactInfoDTO;

import java.util.List;

public interface ContactInfoDAO {
    void addContactInfo(String name, String phone, String email);

    List<ContactInfoDTO> getAllContactsInfo();
}
