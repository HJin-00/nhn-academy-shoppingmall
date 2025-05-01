package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.user.domain.Address;
import com.nhnacademy.shoppingmall.user.repository.AddressRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AddressRepositoryImpl implements AddressRepository {

    @Override
    public int save(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        if (connection == null) {
            throw new RuntimeException("Failed to obtain connection");
        }

        String sql = "INSERT INTO address (user_id, address_name, address_detail, created_at) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, address.getUserId());
            statement.setString(2, address.getAddressName());
            statement.setString(3, address.getAddressDetail());
            statement.setTimestamp(4, Timestamp.valueOf(address.getCreatedAt()));

            return statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int update(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        if (connection == null) {
            throw new RuntimeException("Failed to obtain connection");
        }
        String sql = "update address set address_name =? , address_detail =? , created_at = ? where user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, address.getAddressName());
            statement.setString(2, address.getAddressDetail());
            statement.setTimestamp(3, Timestamp.valueOf(address.getCreatedAt()));
            statement.setString(4, address.getUserId());

            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int deleteByUserIdAndAddressName(String userId, String addressName) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        if (connection == null) {
            throw new RuntimeException("Failed to obtain connection");
        }
        String sql = "delete from address where user_id = ? and address_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            statement.setString(2, addressName);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Address findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        if (connection == null) {
            throw new RuntimeException("Failed to obtain connection");
        }

        String sql = "SELECT * FROM adress WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Address(
                            resultSet.getString("user_id"),
                            resultSet.getString("address_name"),
                            resultSet.getTimestamp("created_at").toLocalDateTime(),
                            resultSet.getString("address_detail")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Or you can throw an exception to indicate that the address was not found
    }

    @Override
    public List<Address> findAllAddresses() {
        Connection connection = DbConnectionThreadLocal.getConnection();

        if (connection == null) {
            throw new RuntimeException("Failed to obtain connection");
        }

        List<Address> addressList = new ArrayList<>();
        String sql = "SELECT * FROM address"; // 주소 정보를 가져오는 쿼리

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String addressName = resultSet.getString("address_name");
                String addressDetail = resultSet.getString("address_detail");
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                Address address = new Address();
                address.setUserId(userId);
                address.setAddressName(addressName);
                address.setAddressDetail(addressDetail);
                address.setCreatedAt(createdAt);

                addressList.add(address);
            }

            return addressList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

