package com.dataart.cerebro.dao;

import com.dataart.cerebro.domain.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {
//    ContactInfo addContactInfo(String name, String phone, String email, Connection connection);

    @Query(nativeQuery = true, value = "SELECT 1")
    List<ContactInfo> getAllContactsInfo();

//    ContactInfo getContactInfoById(int id);
}
