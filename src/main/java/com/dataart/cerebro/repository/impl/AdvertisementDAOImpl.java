//package com.dataart.cerebro.dao.impl;
//
//import com.dataart.cerebro.configuration.ConnectionData;
//import com.dataart.cerebro.dao.AdvertisementDAO;
//import com.dataart.cerebro.dao.CategoryDAO;
//import com.dataart.cerebro.dao.ContactInfoDAO;
//import com.dataart.cerebro.domain.*;
//import com.dataart.cerebro.exception.DataProcessingException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//
//import java.sql.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Repository
//@Slf4j
//public class AdvertisementDAOImpl implements AdvertisementDAO {
//    private final ConnectionData connectionData;
//    private final CategoryDAO categoryDAO;
//    private final ContactInfoDAO contactInfoDAO;
//    private static final String BAD_REQUEST = "Bad request for ID {}: {}";
//
//    public AdvertisementDAOImpl(ConnectionData connectionData, CategoryDAO categoryDAO, ContactInfoDAO contactInfoDAO) {
//        this.connectionData = connectionData;
//        this.categoryDAO = categoryDAO;
//        this.contactInfoDAO = contactInfoDAO;
//    }
//
//    @Override
//    public List<Advertisement> getExpiringAdvertisements() {
//        String sql = "SELECT * FROM advertisement WHERE status_id = 1 and current_date >= date(end_time) - interval '1 days';";
//        return getAdvertisementsList(sql);
//    }
//
//    @Override
//    public List<Advertisement> getExpiredAdvertisements() {
//        String sql = "SELECT * FROM advertisement WHERE status_id = 1 and current_date >= date(end_time);";
//        return getAdvertisementsList(sql);
//    }
//
//    @Override
//    public void updateAdvertisementStatus(Advertisement advertisement, Status status) {
//        String sql = "UPDATE advertisement SET status_id = ? WHERE id = ?;";
//        int id = advertisement.getId();
//        log.info("Updating status of advertisement (id = {}) from {} to {}", id, advertisement.getStatus(), status);
//        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, status.getId());
//            preparedStatement.setInt(2, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            log.error(BAD_REQUEST, id, e.getMessage(), e);
//            throw new DataProcessingException(e);
//        }
//    }
//
//    @Override
//    public List<Advertisement> getAllActiveAdvertisements() {
//        String sql = "SELECT * FROM advertisement WHERE status_id = 1 ORDER BY publication_time DESC;";
//        return getAdvertisementsList(sql);
//    }
//
//    @Override
//    public Advertisement getAdvertisementById(int id) {
//        String sql = "SELECT * FROM advertisement WHERE id = ?;";
//        log.info("Sending request: get advertisement by id {}", id);
//
//        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return createAdvertisementDTO(resultSet);
//            }
//        } catch (SQLException e) {
//            log.error(BAD_REQUEST, id, e.getMessage(), e);
//            throw new DataProcessingException(e);
//        }
//        return null;
//    }
//
//    @Override
//    public byte[] getImageByAdvertisementId(int id) {
//        String sql = "SELECT image FROM advertisement WHERE id = ?;";
//
//        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return resultSet.getBytes("image");
//            }
//        } catch (SQLException e) {
//            log.error(BAD_REQUEST, id, e.getMessage(), e);
//            throw new DataProcessingException(e);
//        }
//        return new byte[0];
//    }
//
//    @Override
//    public void addAdvertisement(String title, String text, Double price, String address, byte[] image,
//                                 LocalDateTime publicationTime, LocalDateTime endTime,
//                                 Category category, Type type, Status status, ContactInfo contactInfo) {
//        log.info("Creating new advertisement, parameters: title: {}, text: {}, price: {}, address: {}, category: {},type: {}",
//                title, text, price, address, category.getName(), type.getName());
//
//        String sql = "INSERT INTO advertisement " +
//                "(title, text, price, publication_time, expired_time, category_id, type_id, status_id, owner_id)" +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
//
//        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            try {
//                connection.setAutoCommit(false);
//                ContactInfo contactInfoInitial = contactInfoDAO.addContactInfo(contactInfo.getName(), contactInfo.getPhone(),
//                        contactInfo.getEmail(), connection);
//                preparedStatement.setString(1, title);
//                preparedStatement.setString(2, text);
//                preparedStatement.setDouble(3, price);
//                preparedStatement.setString(4, address);
//                preparedStatement.setObject(5, image);
//                preparedStatement.setObject(6, publicationTime);
//                preparedStatement.setObject(7, endTime);
//                preparedStatement.setInt(8, category.getId());
//                preparedStatement.setInt(9, type.getId());
//                preparedStatement.setInt(10, status.getId());
//                preparedStatement.setInt(11, contactInfoInitial.getId());
//                preparedStatement.executeUpdate();
//                connection.commit();
//                log.info("New advertisement is added to data base");
//            } catch (Exception e) {
//                connection.rollback();
//                log.error("Runtime exception: {}", e.getMessage(), e);
//                throw new DataProcessingException(e);
//            }
//        } catch (SQLException e) {
//            log.error("Bad request: {}", e.getMessage(), e);
//            throw new DataProcessingException(e);
//        }
//    }
//
//    private Advertisement createAdvertisementDTO(ResultSet resultSet) throws SQLException {
//        Advertisement advertisement = new Advertisement();
//
//        advertisement.setId(resultSet.getInt("id"));
//        advertisement.setTitle(resultSet.getString("title"));
//        advertisement.setText(resultSet.getString("text"));
//        advertisement.setPrice(resultSet.getDouble("price"));
//        advertisement.setPublicationTime(resultSet.getObject("publication_time", LocalDateTime.class));
//        advertisement.setExpiredTime(resultSet.getObject("end_time", LocalDateTime.class));
//
//        int categoryId = resultSet.getInt("category_id");
//        advertisement.setCategory(categoryDAO.getCategoryById(categoryId));
//
//        int statusId = resultSet.getInt("status_id");
//        advertisement.setStatus(Status.getStatusDTOById(statusId));
//
//        int typeId = resultSet.getInt("type_id");
//        advertisement.setType(Type.getTypeDTOById(typeId));
//
//        int ownerId = resultSet.getInt("owner_id");
//        advertisement.setOwner(contactInfoDAO.getContactInfoById(ownerId));
//
//        return advertisement;
//    }
//
//    private List<Advertisement> getAdvertisementsList(String sql) {
//        List<Advertisement> advertisementList = new ArrayList<>();
//        log.info("Connecting to Data Base and sending request: {}", sql);
//        try (Connection connection = DriverManager.getConnection(connectionData.URL, connectionData.USER, connectionData.PASSWORD);
//             Statement statement = connection.createStatement()) {
//
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                advertisementList.add(createAdvertisementDTO(resultSet));
//            }
//            log.info("Result is received");
//        } catch (SQLException e) {
//            log.error("Bad request; {}", e.getMessage(), e);
//            throw new DataProcessingException(e);
//        }
//        return advertisementList;
//    }
//}
