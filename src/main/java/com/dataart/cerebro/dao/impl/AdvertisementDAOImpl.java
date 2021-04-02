package com.dataart.cerebro.dao.impl;

import com.dataart.cerebro.configuration.ConnectionData;
import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dao.CategoryDAO;
import com.dataart.cerebro.dao.ContactInfoDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.dto.StatusDTO;
import com.dataart.cerebro.dto.TypeDTO;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class AdvertisementDAOImpl implements AdvertisementDAO {
    private final ConnectionData connectionData;
    private final CategoryDAO categoryDAO;
    private final ContactInfoDAO contactInfoDAO;
    public static final String BAD_REQUEST = "Bad request for ID {}: {}";

    public AdvertisementDAOImpl(ConnectionData connectionData, CategoryDAO categoryDAO, ContactInfoDAO contactInfoDAO) {
        this.connectionData = connectionData;
        this.categoryDAO = categoryDAO;
        this.contactInfoDAO = contactInfoDAO;
    }

    @Override
    public List<AdvertisementDTO> getExpiringAdvertisements() {
        String sql = "SELECT * FROM advertisement WHERE status_id = 1 and current_date = date(end_time) - interval '1 days';";
        return getAdvertisementsList(sql);
    }

    @Override
    public List<AdvertisementDTO> getExpiredAdvertisements() {
        String sql = "SELECT * FROM advertisement WHERE status_id = 1 and current_date = date(end_time);";
        return getAdvertisementsList(sql);
    }

    @Override
    public void updateAdvertisementStatus(int statusId, AdvertisementDTO advertisement) {
        String sql = "UPDATE advertisement SET status_id = ? WHERE id = ?;";
        int id = advertisement.getId();
        log.info("Updating status of advertisement (id = {}) from {} to {}", id, advertisement.getStatus(), StatusDTO.CLOSED);
        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, statusId);
            preparedStatement.setInt(2, advertisement.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            log.error(BAD_REQUEST, id, e.getMessage(), e);
        }
    }

    @Override
    public List<AdvertisementDTO> getAllActiveAdvertisements() {
        String sql = "SELECT * FROM advertisement WHERE status_id = 1 ORDER BY publication_time DESC;";
        return getAdvertisementsList(sql);
    }

    @Override
    public AdvertisementDTO getAdvertisementById(int id) {
        String sql = "SELECT * FROM advertisement WHERE id = ?;";
        log.info("Connecting to Data Base and sending request");
        log.info("Sending request: get advertisement by id {}", id);

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createAdvertisementDTO(resultSet);
            }
        } catch (Exception e) {
            log.error(BAD_REQUEST, id, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public byte[] getImageByAdvertisementId(int id) {
        String sql = "SELECT image FROM advertisement WHERE id = ?;";

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBytes("image");
            }
        } catch (Exception e) {
            log.error(BAD_REQUEST, id, e.getMessage(), e);
        }
        return new byte[0];
    }

    @Override
    public void addAdvertisement(String title, String text, Double price, String address, byte[] image,
                                 LocalDateTime publicationTime, LocalDateTime endTime,
                                 int categoryId, int typeId, int statusId, ContactInfoDTO contactInfo) {

        String sql = "INSERT INTO advertisement " +
                "(title, text, price, address, image, publication_time, end_time, category_id, type_id, status_id, owner_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try {
                connection.setAutoCommit(false);
                ContactInfoDTO contactInfoInitial = contactInfoDAO.addContactInfo(contactInfo.getName(), contactInfo.getPhone(),
                        contactInfo.getEmail(), connection);
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, text);
                preparedStatement.setDouble(3, price);
                preparedStatement.setString(4, address);
                preparedStatement.setObject(5, image);
                preparedStatement.setObject(6, publicationTime);
                preparedStatement.setObject(7, endTime);
                preparedStatement.setInt(8, categoryId);
                preparedStatement.setInt(9, typeId);
                preparedStatement.setInt(10, statusId);
                preparedStatement.setInt(11, contactInfoInitial.getId());
                preparedStatement.executeUpdate();
                connection.commit();
                log.info("New advertisement is added to data base");
            } catch (Exception e) {
                connection.rollback();
                log.error("Runtime exception: {}", e.getMessage());
            }
        } catch (SQLException e) {
            log.error("Bad request; {}", e.getMessage());
        }
    }

    private AdvertisementDTO createAdvertisementDTO(ResultSet resultSet) throws SQLException, NotFoundException {
        AdvertisementDTO advertisement = new AdvertisementDTO();

        advertisement.setId(resultSet.getInt("id"));
        advertisement.setTitle(resultSet.getString("title"));
        advertisement.setText(resultSet.getString("text"));
        advertisement.setPrice(resultSet.getDouble("price"));
        advertisement.setImage(resultSet.getBytes("image"));
        advertisement.setAddress(resultSet.getString("address"));

        advertisement.setPublicationTime(resultSet.getObject("publication_time", LocalDateTime.class));
        advertisement.setEndTime(resultSet.getObject("end_time", LocalDateTime.class));

        int categoryId = resultSet.getInt("category_id");
        advertisement.setCategory(categoryDAO.getCategoryById(categoryId));

        int statusId = resultSet.getInt("status_id");
        advertisement.setStatus(StatusDTO.getStatusDTOById(statusId));

        int typeId = resultSet.getInt("type_id");
        advertisement.setType(TypeDTO.getTypeDTOById(typeId));

        int ownerId = resultSet.getInt("owner_id");
        advertisement.setOwner(contactInfoDAO.getContactInfoById(ownerId));

        return advertisement;
    }

    private List<AdvertisementDTO> getAdvertisementsList(String sql) {
        List<AdvertisementDTO> advertisementList = new ArrayList<>();
        log.info("Connecting to Data Base and sending request: {}", sql);
        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                advertisementList.add(createAdvertisementDTO(resultSet));
            }
        } catch (SQLException | NotFoundException e) {
            log.error("Bad request");
        }
        log.info("Result is received");
        return advertisementList;
    }
}
