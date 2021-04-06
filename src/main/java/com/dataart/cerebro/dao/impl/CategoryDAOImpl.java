package com.dataart.cerebro.dao.impl;

import com.dataart.cerebro.configuration.ConnectionData;
import com.dataart.cerebro.dao.CategoryDAO;
import com.dataart.cerebro.dao.ServiceDAO;
import com.dataart.cerebro.domain.CategoryDTO;
import com.dataart.cerebro.domain.ServiceDTO;
import com.dataart.cerebro.exception.DataProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@Repository
@Slf4j
public class CategoryDAOImpl implements CategoryDAO {
    private final ConnectionData connectionData;
    private final ServiceDAO serviceDAO;

    public CategoryDAOImpl(ConnectionData connectionData, ServiceDAO serviceDAO) {
        this.connectionData = connectionData;
        this.serviceDAO = serviceDAO;
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
            throw new DataProcessingException(e);
        }
        return null;
    }

    @Override
    public Set<CategoryDTO> getAllCategories() {
        String sql = "SELECT * FROM category ORDER BY id;";
        Set<CategoryDTO> categorySet = new HashSet<>();
        log.info("Connecting to Data Base and sending request: {}", sql);

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                categorySet.add(createCategoryDTO(resultSet));
            }
        } catch (SQLException e) {
            log.error("Bad request: {}", e.getMessage(), e);
            throw new DataProcessingException(e);
        }
        log.info("Result is received");
        return categorySet;
    }

    private CategoryDTO createCategoryDTO(ResultSet resultSet) throws SQLException {
        CategoryDTO category = new CategoryDTO();
        Set<ServiceDTO> servicesSet = serviceDAO.getAllServicesByCategoryId(resultSet.getInt("id"));
        category.setId(resultSet.getInt("id"));
        category.setName(resultSet.getString("name"));
        category.setServicesSet(servicesSet);
        return category;
    }
}
