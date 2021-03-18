package com.dataart.cerebro.dao;

import com.dataart.cerebro.connection.ConnectionData;
import com.dataart.cerebro.dto.AdvertisementDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class AdvertisementDAOImpl implements AdvertisementDAO {
    final ConnectionData connectionData;
    final CategoryDAO categoryDAO;
    final StatusDAO statusDAO;
    final TypeDAO typeDAO;

    public AdvertisementDAOImpl(ConnectionData connectionData, CategoryDAO categoryDAO, StatusDAO statusDAO, TypeDAO typeDAO) {
        this.connectionData = connectionData;
        this.categoryDAO = categoryDAO;
        this.statusDAO = statusDAO;
        this.typeDAO = typeDAO;
    }

    @Override
    public List<AdvertisementDTO> getAllAdvertisements() {
        String sql = "SELECT * FROM advertisement;";
        List<AdvertisementDTO> advertisementList = new ArrayList<>();

        log.info("Connecting to Data Base and sending request");
        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                advertisementList.add(createAdvertisementDTO(resultSet));
            }
        } catch (SQLException e) {
            log.error("Bad request");
        }
        log.info("Result is received");
        return advertisementList;
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
            log.error("Bad request for ID {}: {}", id, e.getMessage(), e);
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
            log.error("Bad request for ID {}: {}", id, e.getMessage(), e);
        }
        return new byte[0];
    }

    @Override
    public void addAdvertisement(String title, String text, Double price, String address, LocalDateTime publicationTime, LocalDateTime endTime,
                                 int categoryId, int typeId, int statusId) {
        String sql = "INSERT INTO advertisement (title, text, price, address,publication_time, end_time, category_id, type_id, status_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?);";

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, text);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, address);
            preparedStatement.setObject(5, publicationTime);
            preparedStatement.setObject(6, endTime);
            preparedStatement.setInt(7, categoryId);
            preparedStatement.setInt(8, typeId);
            preparedStatement.setInt(9, statusId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("Bad request; {}", e.getMessage(), e);
        }
    }

    private AdvertisementDTO createAdvertisementDTO(ResultSet resultSet) throws SQLException {
        AdvertisementDTO advertisementDTO = new AdvertisementDTO();

        advertisementDTO.setId(resultSet.getInt("id"));
        advertisementDTO.setTitle(resultSet.getString("title"));
        advertisementDTO.setText(resultSet.getString("text"));
        advertisementDTO.setPrice(resultSet.getDouble("price"));
        advertisementDTO.setImage(resultSet.getBytes("image"));
        advertisementDTO.setAddress(resultSet.getString("address"));

        advertisementDTO.setPublicationTime(resultSet.getObject("publication_time", LocalDateTime.class));
        advertisementDTO.setEndTime(resultSet.getObject("end_time", LocalDateTime.class));

        int categoryId = resultSet.getInt("category_id");
        advertisementDTO.setCategoryDTO(categoryDAO.getCategoryById(categoryId));

        int statusId = resultSet.getInt("status_id");
        advertisementDTO.setStatusDTO(statusDAO.getStatusById(statusId));

        int typeId = resultSet.getInt("type_id");
        advertisementDTO.setTypeDTO(typeDAO.getTypeById(typeId));

//        advertisementDTO.setStatusId(resultSet.getInt("owner_id"));

        return advertisementDTO;
    }
}
