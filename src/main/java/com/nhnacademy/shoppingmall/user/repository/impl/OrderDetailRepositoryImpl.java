package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.OrderDetail;
import com.nhnacademy.shoppingmall.user.repository.OrderDetailRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailRepositoryImpl implements OrderDetailRepository {
    @Override
    public int save(OrderDetail orderDetail) {
        String sql = "INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        Connection conn = DbConnectionThreadLocal.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, orderDetail.getOrderId());
            ps.setInt(2, orderDetail.getProductId());
            ps.setInt(3, orderDetail.getQuantity());
            ps.setInt(4, orderDetail.getPrice());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("주문 상세 저장 실패", e);
        }
        return -1;
    }

    @Override
    public List<OrderDetail> findByOrderId(int orderId) {
        String sql = "SELECT * FROM order_detail WHERE order_id = ?";
        Connection conn = DbConnectionThreadLocal.getConnection();
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orderDetails.add(new OrderDetail(
                            rs.getInt("order_detail_id"),
                            rs.getInt("order_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity"),
                            rs.getInt("price")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("주문 상세 조회 실패", e);
        }
        return orderDetails;
    }
}
