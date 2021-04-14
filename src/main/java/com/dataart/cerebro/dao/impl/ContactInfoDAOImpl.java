//package com.dataart.cerebro.dao.impl;
//
//import com.dataart.cerebro.configuration.ConnectionData;
//import com.dataart.cerebro.dao.ContactInfoDAO;
//import com.dataart.cerebro.domain.ContactInfo;
//import com.dataart.cerebro.exception.DataProcessingException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Repository
//@Slf4j
//public class ContactInfoDAOImpl implements ContactInfoDAO {
//    private static final String CONNECTING = "Connecting to Data Base and sending request";
//    private final ConnectionData connectionData;
//
//    public ContactInfoDAOImpl(ConnectionData connectionData) {
//        this.connectionData = connectionData;
//    }
//
//    @Override
//    public ContactInfo addContactInfo(String name, String phone, String email, Connection connection) {
//        String sql = "INSERT INTO contact_info (name, phone, email) VALUES (?,?,?);";
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, phone);
//            preparedStatement.setString(3, email);
//            preparedStatement.executeUpdate();
//
//            ResultSet resultSet = preparedStatement.getGeneratedKeys();
//            if (resultSet.next()) {
//                int lastInsertedId = resultSet.getInt(1);
//                log.info("New contact is added to data base");
//                return getContactInfoById(lastInsertedId, connection);
//            } else {
//                connection.rollback();
//            }
//        } catch (SQLException e) {
//            log.error("Bad request; {}", e.getMessage(), e);
//            throw new DataProcessingException(e);
//        }
//        return null;
//    }
//
//    @Override
//    public List<ContactInfo> getAllContactsInfo() {
//        String sql = "SELECT * FROM contact_info;";
//        List<ContactInfo> contactInfoList = new ArrayList<>();
//        log.info(CONNECTING);
//
//        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
//             Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                contactInfoList.add(createContactInfoDTO(resultSet));
//            }
//        } catch (SQLException e) {
//            log.error("Bad request; {}", e.getMessage(), e);
//            throw new DataProcessingException(e);
//        }
//        return contactInfoList;
//    }
//
//    @Override
//    public ContactInfo getContactInfoById(int id) {
//        log.info(CONNECTING);
//        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD)) {
//            return getContactInfoById(id, connection);
//
//        } catch (SQLException e) {
//            log.error("Bad request for ID {}: {}", id, e.getMessage(), e);
//            throw new DataProcessingException(e);
//        }
//    }
//
//    private ContactInfo getContactInfoById(int id, Connection connection) {
//        String sql = "SELECT * FROM contact_info WHERE id = ?;";
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                return createContactInfoDTO(resultSet);
//            }
//
//        } catch (Exception e) {
//            log.error("Bad request for ID {}: {}", id, e.getMessage(), e);
//            throw new DataProcessingException(e);
//        }
//        return null;
//    }
//
//    private ContactInfo createContactInfoDTO(ResultSet resultSet) throws SQLException {
//        ContactInfo contactInfo = new ContactInfo();
//        contactInfo.setId(resultSet.getInt("id"));
//        contactInfo.setName(resultSet.getString("name"));
//        contactInfo.setPhone(resultSet.getString("phone"));
//        contactInfo.setEmail((resultSet.getString("email")));
//        return contactInfo;
//    }
//}
