package com.dataart.cerebro.dao;

import com.dataart.cerebro.configuration.ConnectionData;
import com.dataart.cerebro.dto.StatusDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Slf4j
public class StatusDAOImpl implements StatusDAO {
    final ConnectionData connectionData;

    public StatusDAOImpl(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    @Override
    public StatusDTO getStatusById(int statusId) {
        String sql = "SELECT * FROM status WHERE id = ?;";
        log.info("Sending request: get status by id {}", statusId);

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, statusId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createStatusDTO(resultSet);
            }
        } catch (SQLException e) {
            log.error("Bad request for ID {}: {}", statusId, e.getMessage(), e);
        }
        return null;
    }

    private StatusDTO createStatusDTO(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        return StatusDTO.valueOf(name);
    }
}
