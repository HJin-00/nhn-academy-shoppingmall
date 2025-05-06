package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.Order;
import com.nhnacademy.shoppingmall.user.repository.OrderRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public int save(Order order) {
        String sql = "INSERT INTO orders (user_id, used_point, earn_point, order_status, created_at) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, order.getUserId());
            ps.setInt(2, order.getUsedPoint());
            ps.setInt(3, order.getEarnPoint());
            ps.setString(4, order.getOrderStatus());
            ps.setTimestamp(5, Timestamp.valueOf(order.getCreatedAt()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("주문 저장 실패", e);
        }
        return -1;
    }

    @Override
    public Optional<Order> findById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Order(
                            rs.getInt("order_id"),
                            rs.getString("user_id"),
                            rs.getInt("used_point"),
                            rs.getInt("earn_point"),
                            rs.getString("order_status"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("주문 조회 실패", e);
        }
        return Optional.empty();
    }
    @Override
    public List<Order> findPage(int offset, int limit) {
        String sql = "SELECT * FROM orders ORDER BY created_at DESC LIMIT ? OFFSET ?";
        List<Order> orders = new ArrayList<>();
        Connection conn = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUserId(rs.getString("user_id"));
                    order.setUsedPoint(rs.getInt("used_point"));
                    order.setEarnPoint(rs.getInt("earn_point"));
                    order.setOrderStatus(rs.getString("order_status"));
                    order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("주문 페이지 조회 실패", e);
        }

        return orders;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM orders";
        Connection conn = DbConnectionThreadLocal.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("주문 수 카운트 실패", e);
        }

        return 0;
    }
}
