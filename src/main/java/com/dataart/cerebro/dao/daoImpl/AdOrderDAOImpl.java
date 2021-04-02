package com.dataart.cerebro.dao.daoImpl;

import com.dataart.cerebro.configuration.ConnectionData;
import com.dataart.cerebro.dao.AdOrderDAO;
import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dao.ContactInfoDAO;
import com.dataart.cerebro.dao.ServiceDAO;
import com.dataart.cerebro.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Set;

@Repository
@Slf4j
public class AdOrderDAOImpl implements AdOrderDAO {
    private final ConnectionData connectionData;
    private final ContactInfoDAO contactInfoDAO;
    private final AdvertisementDAO advertisementDAO;
    private final ServiceDAO serviceDAO;

    public AdOrderDAOImpl(ConnectionData connectionData, ContactInfoDAO contactInfoDAO, AdvertisementDAO advertisementDAO, ServiceDAO serviceDAO) {
        this.connectionData = connectionData;
        this.contactInfoDAO = contactInfoDAO;
        this.advertisementDAO = advertisementDAO;
        this.serviceDAO = serviceDAO;
    }

    @Override
    public AdOrderDTO addAdOrder(AdOrderDTO adOrderDTO, LocalDateTime orderTime, AdvertisementDTO advertisementDTO, ContactInfoDTO customerInfo) {
        String sql = "INSERT INTO adorder(order_time, total_price, advertisement_id, contact_info_id) VALUES (?, ?, ?, ?);";
        log.info("Creating a new order for advertisement (id = {})", advertisementDTO.getId());

        try(Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            try {
                connection.setAutoCommit(false);
                ContactInfoDTO contactInfoInitial = contactInfoDAO.addContactInfo(customerInfo.getName(), customerInfo.getPhone(),
                        customerInfo.getEmail(), connection);

                preparedStatement.setObject(1, orderTime);
                preparedStatement.setDouble(2, adOrderDTO.getTotalPrice());
                preparedStatement.setInt(3, advertisementDTO.getId());
                preparedStatement.setInt(4, contactInfoInitial.getId());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if(resultSet.next())
                {
                    int lastInsertedId = resultSet.getInt(1);
                    Set<ServiceDTO> servicesSet = adOrderDTO.getServicesSet();

                    setOrderServices(lastInsertedId, servicesSet, connection);


                    advertisementDAO.updateAdvertisementStatus(StatusDTO.SOLD.getId(), advertisementDTO);
                    connection.commit();
                    log.info("New order is added to data base");
                    return getAdOrderById(lastInsertedId);

                }else {
                    connection.rollback();
                }

            }catch (Exception e) {
                connection.rollback();
                log.error("Runtime exception: {}", e.getMessage());
            }
        }catch (SQLException e) {
            log.error("Bad request; {}", e.getMessage());
        }
        return null;
    }

    @Override
    public AdOrderDTO getAdOrderById(int id) {
        String sql = "SELECT * FROM adorder WHERE id = ?;";

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createAdOrder(resultSet);
            }
        } catch (Exception e) {
            log.error("Bad request", id, e.getMessage(), e);
        }
        return null;

    }

    private AdOrderDTO createAdOrder(ResultSet resultSet) throws SQLException {
        AdOrderDTO adOrderDTO = new AdOrderDTO();

        adOrderDTO.setId(resultSet.getInt("id"));
        adOrderDTO.setOrderTime(resultSet.getObject("order_time", LocalDateTime.class));
        adOrderDTO.setTotalPrice(resultSet.getDouble("total_price"));

        int advertisementId = resultSet.getInt("advertisement_id");
        adOrderDTO.setAdvertisementDTO(advertisementDAO.getAdvertisementById(advertisementId));

        int contactInfoId = resultSet.getInt("contact_info_id");
        adOrderDTO.setContactInfoDTO(contactInfoDAO.getContactInfoById(contactInfoId));

        adOrderDTO.setServicesSet(serviceDAO.getAllServicesByOrderId(resultSet.getInt("id")));

        return adOrderDTO;
    }

    private void setOrderServices (int lastInsertedId, Set<ServiceDTO> servicesSet, Connection connection) throws SQLException {
        String sqlInsert = "INSERT INTO  services_of_order (services_id, adorder_id) VALUES (?, ?);";
        try(PreparedStatement prStatement = connection.prepareStatement(sqlInsert)) {
            for (ServiceDTO serviceDTO : servicesSet) {
                prStatement.setInt(1, serviceDTO.getId());
                prStatement.setInt(2, lastInsertedId);
                prStatement.executeUpdate();
            }
        }
    }
}