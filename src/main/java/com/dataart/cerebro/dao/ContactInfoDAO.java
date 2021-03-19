package com.dataart.cerebro.dao;

import com.dataart.cerebro.dto.ContactInfoDTO;

import java.util.List;

public interface ContactInfoDAO {
    ContactInfoDTO addContactInfo(String name, String phone, String email);

    List<ContactInfoDTO> getAllContactsInfo();

    ContactInfoDTO getContactInfoByName(String name);

    ContactInfoDTO getContactInfoById(int id);
}
