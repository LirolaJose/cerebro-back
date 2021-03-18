package com.dataart.cerebro.dao;

import com.dataart.cerebro.connection.ConnectionData;
import com.dataart.cerebro.dto.CategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@Repository
@Slf4j
public class CategoryDAOImpl implements CategoryDAO {
    final
    ConnectionData connectionData;

    public CategoryDAOImpl(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    @Override
    public CategoryDTO getCategoryById(int categoryId) {
        String sql = "SELECT * FROM category WHERE id = ?;";

        log.info("Sending request: get category by id {}", categoryId);

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createCategoryDTO(resultSet);
            }
        } catch (SQLException e) {
            log.error("Bad request for ID {}: {}", categoryId, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Set<CategoryDTO> getAllCategory() {
        String sql = "SELECT * FROM category;";
        Set<CategoryDTO> categorySet = new HashSet<>();

        log.info("Connecting to Data Base and sending request");
        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                categorySet.add(createCategoryDTO(resultSet));
            }
        } catch (SQLException e) {
            log.error("Bad request");
        }
        log.info("Result is received");
        return categorySet;
    }

    private CategoryDTO createCategoryDTO(ResultSet resultSet) throws SQLException {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(resultSet.getInt("id"));
        categoryDTO.setName(resultSet.getString("name"));

        return categoryDTO;
    }
}
