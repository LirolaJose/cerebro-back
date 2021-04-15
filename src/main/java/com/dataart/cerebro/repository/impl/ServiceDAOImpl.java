//package com.dataart.cerebro.dao.impl;
//
//import com.dataart.cerebro.configuration.ConnectionData;
//import com.dataart.cerebro.dao.ServiceDAO;
//import com.dataart.cerebro.domain.Service;
//import com.dataart.cerebro.exception.DataProcessingException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//
//import java.sql.*;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Repository
//@Slf4j
//public class ServiceDAOImpl implements ServiceDAO {
//    private final ConnectionData connectionData;
//
//    public ServiceDAOImpl(ConnectionData connectionData) {
//        this.connectionData = connectionData;
//    }
//
//    @Override
//    public Set<Service> getAllServicesByCategoryId(int id) {
//        String sql = "SELECT * FROM service join services_of_category soc on service.id = soc.services_id WHERE category_id = ?;";
//        return getServicesSet(sql, id);
//    }
//
//
//    @Override
//    public Set<Service> getAllServicesByOrderId(int id) {
//        String sql = "SELECT * FROM service JOIN services_of_order soo on service.id = soo.services_id WHERE adorder_id = ?;";
//        return getServicesSet(sql, id);
//    }
//
//    @Override
//    public Double getTotalPriceServices(Set<Service> servicesSet) {
//        if (servicesSet.isEmpty()) {
//            log.info("The order without additional services");
//            return 0.0;
//        }
//        log.info("Calculating the total price of additional services");
//        String sql = "SELECT SUM(price) FROM service WHERE id IN %s;";
//
//        String sqlIN = servicesSet.stream()
//                .map(Service::getId)
//                .map(String::valueOf)
//                .collect(Collectors.joining(",", "(", ")"));
//
//        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
//             PreparedStatement preparedStatement = connection.prepareStatement(String.format(sql, sqlIN))) {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return resultSet.getDouble("sum");
//            }
//        } catch (SQLException e) {
//            log.info("Bad request; {}", e.getMessage(), e);
//            throw new DataProcessingException(e);
//        }
//        return 0.0;
//    }
//
//    private Service createServiceDTO(ResultSet resultSet) throws SQLException {
//        Service service = new Service();
//        service.setId(resultSet.getInt("id"));
//        service.setName(resultSet.getString("name"));
//        service.setPrice(resultSet.getDouble("price"));
//        return service;
//    }
//
//    private Set<Service> getServicesSet(String sql, int id) {
//        Set<Service> servicesSet = new HashSet<>();
//        log.info("Sending request: {}", sql);
//
//        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                servicesSet.add(createServiceDTO(resultSet));
//            }
//        } catch (SQLException e) {
//            log.error("Bad request {}", e.getMessage(), e);
//            throw new DataProcessingException(e);
//        }
//        log.info("Result is received");
//        return servicesSet;
//    }
//}
