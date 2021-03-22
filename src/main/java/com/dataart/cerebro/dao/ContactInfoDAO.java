package com.dataart.cerebro.dao;

import com.dataart.cerebro.dto.ContactInfoDTO;

import java.sql.Connection;
import java.util.List;

public interface ContactInfoDAO {
    ContactInfoDTO addContactInfo(String name, String phone, String email, Connection connection);

    List<ContactInfoDTO> getAllContactsInfo();

    ContactInfoDTO getContactInfoByName(String name, Connection connection);

    ContactInfoDTO getContactInfoById(int id);
}
