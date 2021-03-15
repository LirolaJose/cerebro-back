package com.dataart.cerebro.dao;

import com.dataart.cerebro.connection.ConnectionData;
import com.dataart.cerebro.dto.AdvertisementDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Repository
@Slf4j
public class AdvertisementDAOImpl implements AdvertisementDAO {
    final
ConnectionData connectionData;

    public AdvertisementDAOImpl(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    @Override
    public List<AdvertisementDTO> getAllAdvertisements() {
        String sql = "SELECT * FROM advertisement;";
        List<AdvertisementDTO> advertisementDTOList = new ArrayList<>();

//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        log.info("Connecting to Data Base and sending request");
        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                advertisementDTOList.add(createAdvertisementDTO(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return advertisementDTOList;
    }

    private AdvertisementDTO createAdvertisementDTO(ResultSet resultSet) throws SQLException {
        AdvertisementDTO advertisementDTO = new AdvertisementDTO();
        log.info("Creating AdvertisementDTO");

        advertisementDTO.setId(resultSet.getInt("id"));
        advertisementDTO.setTitle(resultSet.getString("title"));
        advertisementDTO.setText(resultSet.getString("text"));
        advertisementDTO.setPrice(resultSet.getDouble("price"));
//        advertisementDTO.setImage((Image) resultSet.getObject("image"));
        advertisementDTO.setAddress(resultSet.getString("address"));
//        advertisementDTO.setPublicationTime((LocalDateTime) resultSet.getObject("publication_time"));
//        advertisementDTO.setEndTime((LocalDateTime) resultSet.getObject("end_time"));
        advertisementDTO.setCategoryId((resultSet.getInt("category_id")));
        advertisementDTO.setTypeId(resultSet.getInt("type_id"));
        advertisementDTO.setStatusId(resultSet.getInt("status_id"));
        advertisementDTO.setStatusId(resultSet.getInt("owner_id"));

        return advertisementDTO;
    }
}
