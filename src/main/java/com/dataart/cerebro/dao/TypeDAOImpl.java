package com.dataart.cerebro.dao;

import com.dataart.cerebro.connection.ConnectionData;
import com.dataart.cerebro.dto.TypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Slf4j
public class TypeDAOImpl implements TypeDAO {
    final ConnectionData connectionData;

    public TypeDAOImpl(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    @Override
    public TypeDTO getTypeById(int statusId) {
        String sql = "SELECT * FROM type WHERE id = ?;";
        log.info("Sending request: get type by id {}", statusId);

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, statusId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createTypeDTO(resultSet);
            }
        } catch (SQLException e) {
            log.error("Bad request for ID {}: {}", statusId, e.getMessage(), e);
        }
        return null;
    }

    private TypeDTO createTypeDTO(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        return TypeDTO.valueOf(name);
    }
}
