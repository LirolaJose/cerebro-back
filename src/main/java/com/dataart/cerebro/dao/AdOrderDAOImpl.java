package com.dataart.cerebro.dao;

import com.dataart.cerebro.configuration.ConnectionData;
import com.dataart.cerebro.dto.AdOrderDTO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.dto.StatusDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Repository
@Slf4j
public class AdOrderDAOImpl implements AdOrderDAO {
    final ConnectionData connectionData;
    final ContactInfoDAO contactInfoDAO;
    final AdvertisementDAO advertisementDAO;

    public AdOrderDAOImpl(ConnectionData connectionData, ContactInfoDAO contactInfoDAO, AdvertisementDAO advertisementDAO) {
        this.connectionData = connectionData;
        this.contactInfoDAO = contactInfoDAO;
        this.advertisementDAO = advertisementDAO;
    }

    @Override
    public void addAdOrder(AdOrderDTO adOrderDTO, LocalDateTime orderTime, AdvertisementDTO advertisementDTO, ContactInfoDTO customerInfo) {
        String sql = "INSERT INTO adorder(order_time, total_price, advertisement_id, contact_info_id) VALUES (?, ?, ?, ?);";
        log.info("Creating a new order for advertisement (id = {})", advertisementDTO.getId());

        try(Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try {
                connection.setAutoCommit(false);
                ContactInfoDTO contactInfoInitial = contactInfoDAO.addContactInfo(customerInfo.getName(), customerInfo.getPhone(),
                        customerInfo.getEmail(), connection);
                preparedStatement.setObject(1, orderTime);
                preparedStatement.setDouble(2, adOrderDTO.getTotalPrice());
                preparedStatement.setInt(3, advertisementDTO.getId());
                preparedStatement.setInt(4, contactInfoInitial.getId());
                preparedStatement.executeUpdate();

                advertisementDAO.updateAdvertisementStatus(StatusDTO.SOLD.getId(), advertisementDTO);
                connection.commit();
                log.info("New order is added to data base");
            }catch (Exception e) {
                connection.rollback();
                log.error("Runtime exception: {}", e.getMessage());
            }
        }catch (SQLException e) {
            log.error("Bad request; {}", e.getMessage());
        }
    }
}
