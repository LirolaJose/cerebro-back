package com.dataart.cerebro.dao;

import com.dataart.cerebro.connection.ConnectionData;
import com.dataart.cerebro.dto.CategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Slf4j
public class CategoryDAOImpl implements CategoryDAO {
    final
    ConnectionData connectionData;

    public CategoryDAOImpl(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    @Override
    public CategoryDTO getCategoryDTObyId(int categoryId) {
        String sql = "SELECT * FROM category WHERE id = ?;";

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return createCategoryDTO(resultSet);
            }
        } catch (SQLException e) {
            log.error("Wrong request");
        }
        return null;
    }

    private CategoryDTO createCategoryDTO(ResultSet resultSet) {
        CategoryDTO categoryDTO = new CategoryDTO();
        try {
            categoryDTO.setId(resultSet.getInt("id"));
            categoryDTO.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryDTO;
    }
}
