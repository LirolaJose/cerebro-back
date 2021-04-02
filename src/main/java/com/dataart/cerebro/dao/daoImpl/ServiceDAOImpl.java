package com.dataart.cerebro.dao.daoImpl;

import com.dataart.cerebro.configuration.ConnectionData;
import com.dataart.cerebro.dao.ServiceDAO;
import com.dataart.cerebro.dto.ServiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ServiceDAOImpl implements ServiceDAO {
    final ConnectionData connectionData;

    public ServiceDAOImpl(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    @Override
    public Set<ServiceDTO> getAllServicesByCategoryId(int id) {
        String sql = "SELECT * FROM service join services_of_category soc on service.id = soc.services_id WHERE category_id = ?;";
        Set<ServiceDTO> servicesSet = new HashSet<>();
        log.info("Sending request: {}", sql);

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                servicesSet.add(createServiceDTO(resultSet));
            }
        } catch (SQLException e) {
            log.error("Bad request");
        }
        log.info("Result is received");
        return servicesSet;
    }

    @Override
    public Set<ServiceDTO> getAllServicesByOrderId(int id) {
        String sql = "SELECT * FROM service JOIN services_of_order soo on service.id = soo.services_id WHERE adorder_id = ?;";
        Set<ServiceDTO> servicesSet = new HashSet<>();
        log.info("Sending request: {}", sql);

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                servicesSet.add(createServiceDTO(resultSet));
            }
        } catch (SQLException e) {
            log.error("Bad request");
        }
        log.info("Result is received");
        return servicesSet;
    }

    @Override
    public ServiceDTO getServiceById(int id) {
        String sql = "SELECT * FROM service WHERE id = ?;";
        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createServiceDTO(resultSet);
            }
        } catch (SQLException e) {
            log.error("Bad request");
        }
        return null;
    }

    @Override
    public Double getTotalPriceServices(Set<ServiceDTO> servicesSet) {
        if (!servicesSet.isEmpty()) {
            log.info("Calculating the total price of additional services");
            String sql = "SELECT SUM(price) FROM service WHERE id IN (?);";
            List<Integer> idList = new ArrayList<>();
            servicesSet.forEach(serviceDTO -> idList.add(serviceDTO.getId()));

            String sqlIN = idList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",", "(", ")"));
            sql = sql.replace("(?)", sqlIN);

            try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getDouble("sum");
                }

            } catch (SQLException e) {
                log.error("Bad request");
            }
        }
        log.info("The order without additional services");
        return 0.0;
    }

    private ServiceDTO createServiceDTO(ResultSet resultSet) throws SQLException {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(resultSet.getInt("id"));
        serviceDTO.setName(resultSet.getString("name"));
        serviceDTO.setPrice(resultSet.getDouble("price"));
        return serviceDTO;
    }
}
