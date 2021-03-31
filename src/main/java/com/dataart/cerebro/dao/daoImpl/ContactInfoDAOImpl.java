package com.dataart.cerebro.dao.daoImpl;

import com.dataart.cerebro.configuration.ConnectionData;
import com.dataart.cerebro.dao.ContactInfoDAO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ContactInfoDAOImpl implements ContactInfoDAO {
    private static  final String CONNECTING = "Connecting to Data Base and sending request";
    private final ConnectionData connectionData;

    public ContactInfoDAOImpl(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    @Override
    public ContactInfoDTO addContactInfo(String name, String phone, String email, Connection connection) {
        String sql = "INSERT INTO contact_info (name, phone, email) VALUES (?,?,?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Bad request; {}", e.getMessage(), e);
        }
        return getContactInfoByName(name, connection);
    }

    @Override
    public List<ContactInfoDTO> getAllContactsInfo() {
        String sql = "SELECT * FROM contact_info;";
        List<ContactInfoDTO> contactInfoList = new ArrayList<>();
        log.info(CONNECTING);

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                contactInfoList.add(createContactInfoDTO(resultSet));
            }
        } catch (SQLException e) {
            log.error("Bad request; {}", e.getMessage(), e);
        }
        return contactInfoList;
    }

    @Override
    public ContactInfoDTO getContactInfoByName(String name, Connection connection) {
        String sql = "SELECT * FROM contact_info WHERE name = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createContactInfoDTO(resultSet);
            }

        } catch (Exception e) {
            log.error("Bad request for ID {}: {}", name, e.getMessage(), e);
        }
        return null;
    }

    public ContactInfoDTO getContactInfoById(int id) {
        String sql = "SELECT * FROM contact_info WHERE id = ?;";
        log.info(CONNECTING);

        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return createContactInfoDTO(resultSet);
            }

        } catch (Exception e) {
            log.error("Bad request for ID {}: {}", id, e.getMessage(), e);
        }
        return null;
    }

    private ContactInfoDTO createContactInfoDTO(ResultSet resultSet) throws SQLException {
        ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
        contactInfoDTO.setId(resultSet.getInt("id"));
        contactInfoDTO.setName(resultSet.getString("name"));
        contactInfoDTO.setPhone(resultSet.getString("phone"));
        contactInfoDTO.setEmail((resultSet.getString("email")));
        return contactInfoDTO;
    }
}
