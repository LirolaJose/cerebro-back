package com.dataart.cerebro.dao;

import com.dataart.cerebro.connection.ConnectionData;
import com.dataart.cerebro.dto.AdvertisementDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class AdvertisementDAOImpl implements AdvertisementDAO {
    final
    ConnectionData connectionData;
    final CategoryDAO categoryDAO;

    public AdvertisementDAOImpl(ConnectionData connectionData, CategoryDAO categoryDAO) {
        this.connectionData = connectionData;
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<AdvertisementDTO> getAllAdvertisements() {
        String sql = "SELECT * FROM advertisement;";
        List<AdvertisementDTO> advertisementDTOList = new ArrayList<>();

        log.info("Connecting to Data Base and sending request");
        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                advertisementDTOList.add(createAdvertisementDTO(resultSet));
            }
        } catch (SQLException e) {
            log.error("Bad request");
        }
        log.info("Result is received");
        return advertisementDTOList;
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
            while (resultSet.next()){
                return createAdvertisementDTO(resultSet);
            }
        } catch (SQLException e){
            log.error("Bad request: {}", e);
        }

        return null;
    }

    private AdvertisementDTO createAdvertisementDTO(ResultSet resultSet) throws SQLException {
        AdvertisementDTO advertisementDTO = new AdvertisementDTO();

        advertisementDTO.setId(resultSet.getInt("id"));
        advertisementDTO.setTitle(resultSet.getString("title"));
        advertisementDTO.setText(resultSet.getString("text"));
        advertisementDTO.setPrice(resultSet.getDouble("price"));
//        advertisementDTO.setImage((Image) resultSet.getObject("image"));
        advertisementDTO.setAddress(resultSet.getString("address"));
        advertisementDTO.setPublicationTime(resultSet.getDate("publication_time"));
        advertisementDTO.setEndTime(resultSet.getDate("end_time"));

        int categoryId = resultSet.getInt("category_id");
        advertisementDTO.setCategoryDTO(categoryDAO.getCategoryDTObyId(categoryId));

//        advertisementDTO.setTypeId(resultSet.getInt("type_id"));
//        advertisementDTO.setStatusId(resultSet.getInt("status_id"));
//        advertisementDTO.setStatusId(resultSet.getInt("owner_id"));

        return advertisementDTO;
    }
}
